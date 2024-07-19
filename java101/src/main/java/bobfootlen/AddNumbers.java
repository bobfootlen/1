package bobfootlen;

import java.util.Scanner;

public class AddNumbers {
    public static void main(String[] args) {
        double sum = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                // Prompt user for the first number
                System.out.print("Enter number: ");
                var number = scanner.nextDouble();

                // Add the two numbers
                sum = number + sum;
            }

            // Display the result
            
            //scanner.close();
        }catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("The sum is: " + sum);
        
    }
}