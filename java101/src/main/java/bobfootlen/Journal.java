package bobfootlen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Journal {
    public static void main(String[] args) throws IOException {
        var filename = "SomeSillyFile.txt";
        OpenOption[] options;
        try (var scanner = new Scanner(System.in)) {
            if(Files.exists(Path.of(filename))){
                Files.readAllLines(Path.of(filename)).stream().forEach((line)->System.out.println(line));
                options = new OpenOption[]{StandardOpenOption.APPEND};
            }else{
                options = new OpenOption[0];
            }
            var lines = new ArrayList<String>();
            System.out.println("Enter more lines below (q alone to quit): ");
            while(true){
                var line = scanner.nextLine();
                if("q".equals(line)){
                    break;
                }
                lines.add(line);
            }
            Files.write(Path.of(filename), lines, options);
            System.out.println("Journal entry recorded.");
        } catch (IOException e) {
            System.err.println("Unable to access file: "+filename);
            System.err.println(e.getMessage());
        }
    }
}
