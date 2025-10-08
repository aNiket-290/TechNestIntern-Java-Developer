
import java.util.ArrayList;
import java.util.Scanner;

// Student class (Encapsulation)
class Student {
    private int id;
    private String name;
    private double marks;

    // Constructor
    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }

    // Setter
    public void setMarks(double marks) {
        this.marks = marks;
    }

    // Display method
    public void display() {
        System.out.println("ID: " + id + " | Name: " + name + " | Marks: " + marks);
    }
}

// Main Class
public class task2studentrecord {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== STUDENT RECORD MANAGEMENT =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    students.add(new Student(id, name, marks));
                    System.out.println(" Student added successfully!");
                }

                case 2 -> {
                    System.out.println("\n--- Student List ---");
                    if (students.isEmpty()) System.out.println("No records found!");
                    else for (Student s : students) s.display();
                }

                case 3 -> {
                    System.out.print("Enter ID to update marks: ");
                    int id = sc.nextInt();
                    boolean found = false;
                    for (Student s : students) {
                        if (s.getId() == id) {
                            System.out.print("Enter new marks: ");
                            double newMarks = sc.nextDouble();
                            s.setMarks(newMarks);
                            System.out.println("✅ Marks updated!");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("❌ Student not found!");
                }

                case 4 -> {
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    students.removeIf(s -> s.getId() == id);
                    System.out.println("✅ Record deleted (if existed).");
                }

                case 5 -> System.out.println("Exiting program...");
                default -> System.out.println("⚠️ Invalid choice! Try again.");
            }
        } while (choice != 5);

        sc.close();
    }
}

