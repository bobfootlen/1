package bobfootlen;
import java.util.Scanner;

public class AddNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for the first number
        System.out.print("Enter first number: ");
        int firstNumber = scanner.nextInt();

        // Prompt user for the second number
        System.out.print("Enter second number: ");
        int secondNumber = scanner.nextInt();

        // Add the two numbers
        int sum = firstNumber + secondNumber;

        // Display the result
        System.out.println("The sum is: " + sum);

        // Close the scanner
        scanner.close();
    }
}