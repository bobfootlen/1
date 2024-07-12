package bobfootlen;

import java.util.Scanner;

/**
 *        what
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try (var scanner = new Scanner(System.in)) {
            System.out.println( "what is your name?" );
            var input = scanner.nextLine();
            System.out.printf("hi %s %n how are you",input);
        }
    }
}
