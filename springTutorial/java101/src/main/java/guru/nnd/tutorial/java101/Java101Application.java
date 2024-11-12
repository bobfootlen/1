package guru.nnd.tutorial.java101;

import java.security.InvalidParameterException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Java101Application {

	@GetMapping("/tictac/bord")
	public String getbord() {
		return bordToString(bord);
	}

	@PutMapping("/tictac/bord")
	public String resetbord(){
		for(var i =0; i < 3; i++) {
			for (var j = 0; j < 3; j++){
				bord[i][j] = '\u00a0';
			}
		}

		return getbord();
	}
	
	@PostMapping("/tictac/bord")
	public ResponseEntity<String> recordmove(int column, int row) {
		if (column > 2 || column < 0 || row > 2 || row < 0) {
			return new ResponseEntity<>("Row or Column Out of Bounds", HttpStatus.BAD_REQUEST);
		}
		if (bord[row][column] != '\u00A0') {
			return new ResponseEntity<>("Space Already Occupied", HttpStatus.BAD_REQUEST);
		}
		bord[row][column] = currentPlayer;
		currentPlayer = currentPlayer == 'x' ? 'o' : 'x';
		return new ResponseEntity<>(getbord(), HttpStatus.OK);
	}

	@GetMapping("/tictac/player")
	public String getPlayer() {
		return "\"" + currentPlayer + "\"";
	}

	private String bordToString(char[][] bord) {
		StringBuilder builder = new StringBuilder(11);
		builder.append('"');
		builder.append(bord[0]);
		builder.append(bord[1]);
		builder.append(bord[2]);
		builder.append('"');
		return builder.toString();
	}

	private char[][] bord = {
			{ '\u00A0', '\u00A0', '\u00A0' },
			{ '\u00A0', '\u00A0', '\u00A0' },
			{ '\u00A0', '\u00A0', '\u00A0' }
	};

	private char currentPlayer = 'x';

	@GetMapping("/add")
	public String add(@RequestParam("operands") double[] operands) {
		double value = 0;
		for (var operand : operands) {
			value += operand;
			System.out.println("adding " + operand);
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
