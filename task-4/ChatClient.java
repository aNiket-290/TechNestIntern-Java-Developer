import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ChatClient {
    private final String host;
    private final int port;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to server " + host + ":" + port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

            // Start a task to read messages from server
            executor.submit(this::readFromServer);

            // Read user input and send to server
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = userIn.readLine()) != null) {
                out.println(input);
                if (input.equalsIgnoreCase("/exit")) break;
            }
            shutdown();

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            shutdown();
        }
    }

    private void readFromServer() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // server closed connection or error reading
        }
    }

    private void shutdown() {
        try {
            if (out != null) out.println("/exit");
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException ignored) {}
        executor.shutdownNow();
        System.out.println("Disconnected.");
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(host, port);
        client.start();
    }
}
