
import java.util.InputMismatchException;
import java.util.Scanner;

// Custom Exception Class
class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

// Calculator Class
class Calculator {
    // Method that may throw an exception
    public double divide(double a, double b) throws DivideByZeroException {
        if (b == 0)
            throw new DivideByZeroException("Cannot divide by zero!");
        return a / b;
    }

    // Other operations
    public double add(double a, double b) { return a + b; }
    public double subtract(double a, double b) { return a - b; }
    public double multiply(double a, double b) { return a * b; }
}

// Main Class
public class task2calculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calc = new Calculator();

        try {
            System.out.println("===== SIMPLE CALCULATOR =====");
            System.out.print("Enter first number: ");
            double num1 = sc.nextDouble();
            System.out.print("Enter second number: ");
            double num2 = sc.nextDouble();

            System.out.println("Choose operation (+, -, *, /): ");
            char op = sc.next().charAt(0);

            double result = 0;

            switch (op) {
                case '+' -> result = calc.add(num1, num2);
                case '-' -> result = calc.subtract(num1, num2);
                case '*' -> result = calc.multiply(num1, num2);
                case '/' -> result = calc.divide(num1, num2);
                default -> System.out.println("Invalid operator!");
            }

            System.out.println("Result = " + result);

        } catch (DivideByZeroException e) {
            System.out.println(" Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println(" Invalid input! Please enter numbers only.");
        } catch (Exception e) {
            System.out.println(" Unexpected error: " + e.getMessage());
        } finally {
            System.out.println(" Calculation complete. Thank you!");
            sc.close();
        }
    }
}
