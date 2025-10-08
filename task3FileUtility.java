import java.io.*;
import java.util.Scanner;

public class task3FileUtility {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== FILE HANDLING UTILITY =====");
            System.out.println("1. Read from a file");
            System.out.println("2. Write to a file");
            System.out.println("3. Append to a file");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> readFile();
                case 2 -> writeFile();
                case 3 -> appendFile();
                case 4 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);
    }

    // READ FILE
    private static void readFile() {
        System.out.print("Enter file name to read: ");
        String fileName = sc.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            System.out.println("\n--- File Content ---");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("--------------------");
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found!");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // WRITE FILE (Overwrite existing content)
    private static void writeFile() {
        System.out.print("Enter file name to write: ");
        String fileName = sc.nextLine();

        System.out.println("Enter content to write (type 'END' to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = sc.nextLine()).equalsIgnoreCase("END")) {
            content.append(line).append("\n");
        }

        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(content.toString());
            System.out.println("File written successfully!");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // APPEND FILE
    private static void appendFile() {
        System.out.print("Enter file name to append: ");
        String fileName = sc.nextLine();

        System.out.println("Enter content to append (type 'END' to finish):");
        StringBuilder content = new StringBuilder();
        String line;
        while (!(line = sc.nextLine()).equalsIgnoreCase("END")) {
            content.append(line).append("\n");
        }

        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(content.toString());
            System.out.println("Content appended successfully!");
        } catch (IOException e) {
            System.out.println("Error appending to file: " + e.getMessage());
        }
    }
}

