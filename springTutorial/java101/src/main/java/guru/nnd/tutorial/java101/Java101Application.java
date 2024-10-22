package guru.nnd.tutorial.java101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Java101Application {

	@GetMapping("/tictac/bord")
	public String getbord(){
		return bordToString(bord);
	}
	@PostMapping("/tictac/bord")
	public String recordmove(){

		return getbord();
	}
	
private String bordToString(char[][] bord){
	StringBuilder builder = new StringBuilder(9);
	builder.append(bord[0]);
	builder.append(bord[1]);
	builder.append(bord[2]);
	return builder.toString();
}
	private char[][] bord = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};

	private char currentPlayer ='x';

    


	@GetMapping("/add")
	public String add(@RequestParam("operands") double[] operands) {
		double value = 0;
		for (var operand : operands) {
			value += operand;
			System.out.println("adding "+ operand);
		}
		return String.valueOf(value);
	}

	@GetMapping("/helloWorld")
	public String home() {
		return "Hello Docker World";
	}

	public static void main(String[] args) {
		SpringApplication.run(Java101Application.class, args);
	}

}
