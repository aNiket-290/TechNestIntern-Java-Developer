import java.io.*;
import java.net.*;
import java.util.Set;
import java.util.concurrent.*;

public class ChatServer {
    private final int port;
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    // thread-safe map for connected clients
    private final ConcurrentMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public ChatServer(int port, int maxThreads) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
        this.pool = Executors.newFixedThreadPool(maxThreads);
        System.out.printf("Server started on port %d with thread pool size %d%n", port, maxThreads);
    }

    public void start() {
        try {
            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            System.out.println("Server stopped accepting connections: " + e.getMessage());
        } finally {
            shutdown();
        }
    }

    private void shutdown() {
        try {
            for (ClientHandler ch : clients.values()) {
                ch.sendMessage("Server is shutting down. Disconnecting...");
                ch.close();
            }
            pool.shutdownNow();
            if (!serverSocket.isClosed()) serverSocket.close();
            System.out.println("Server shutdown complete.");
        } catch (IOException e) {
            System.err.println("Error while shutting down server: " + e.getMessage());
        }
    }

    // Broadcast message to all clients (except optional exclude user)
    private void broadcast(String msg, String excludeUser) {
        for (ClientHandler ch : clients.values()) {
            if (excludeUser != null && excludeUser.equals(ch.getUserName())) continue;
            ch.sendMessage(msg);
        }
    }

    // Fetch list of connected usernames
    private String userList() {
        Set<String> names = clients.keySet();
        if (names.isEmpty()) return "(no users)";
        return String.join(", ", names);
    }

    // Inner class for handling a single client
    private class ClientHandler implements Runnable {
        private final Socket socket;
        private String userName;
        private BufferedReader in;
        private PrintWriter out;
        private volatile boolean connected = true;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public String getUserName() {
            return userName;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

                // Ask for username
                out.println("WELCOME. Enter your username:");
                userName = in.readLine();
                if (userName == null || userName.isBlank()) {
                    out.println("Invalid username. Connection closing.");
                    close();
                    return;
                }

                // Ensure unique username
                if (clients.putIfAbsent(userName, this) != null) {
                    out.println("Username already taken. Connection closing.");
                    close();
                    return;
                }

                out.println("Hello " + userName + "! You can type messages. Commands: /users, /exit");
                broadcast(">> " + userName + " has joined the chat.", userName);
                System.out.println(userName + " connected from " + socket.getRemoteSocketAddress());

                // Listen for messages
                String line;
                while (connected && (line = in.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    if (line.equalsIgnoreCase("/exit")) {
                        out.println("Bye!");
                        break;
                    } else if (line.equalsIgnoreCase("/users")) {
                        out.println("Connected users: " + userList());
                    } else {
                        // Broadcast message
                        String msgToSend = String.format("[%s]: %s", userName, line);
                        broadcast(msgToSend, null);
                        // optionally echo back to sender (already broadcast)
                    }
                }
            } catch (IOException e) {
                System.err.println("Connection error with " + userName + ": " + e.getMessage());
            } finally {
                disconnect();
            }
        }

        void sendMessage(String msg) {
            if (out != null) out.println(msg);
        }

        void close() {
            connected = false;
            try { if (socket != null && !socket.isClosed()) socket.close(); } catch (IOException ignored) {}
        }

        private void disconnect() {
            if (userName != null) {
                clients.remove(userName);
                broadcast(">> " + userName + " has left the chat.", userName);
                System.out.println(userName + " disconnected.");
            }
            close();
        }
    }

    // Simple main to start the server
    public static void main(String[] args) {
        int port = 12345;
        int maxThreads = 50;
        if (args.length >= 1) port = Integer.parseInt(args[0]);
        if (args.length >= 2) maxThreads = Integer.parseInt(args[1]);

        try {
            ChatServer server = new ChatServer(port, maxThreads);
            server.start();
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}

