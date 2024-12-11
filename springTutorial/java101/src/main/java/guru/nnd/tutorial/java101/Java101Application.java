package guru.nnd.tutorial.java101;

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
	public String resetbord() {
		for (var i = 0; i < 3; i++) {
			for (var j = 0; j < 3; j++) {
				bord[i][j] = ' ';
			}
		}

		return getbord();
	}

	@PostMapping("/tictac/bord")
	public ResponseEntity<String> recordmove(String player, int column, int row) {
		if (currentPlayer != player.charAt(0)) {
			return new ResponseEntity<>("It's not your turn.", HttpStatus.BAD_REQUEST);
		}
		if (column > 2 || column < 0 || row > 2 || row < 0) {
			return new ResponseEntity<>("Row or Column Out of Bounds", HttpStatus.BAD_REQUEST);
		}
		if (bord[row][column] != ' ') {
			return new ResponseEntity<>("Space Already Occupied", HttpStatus.BAD_REQUEST);
		}
		bord[row][column] = currentPlayer;
		var endgame = checkEndGame();
		if (endgame == null)
			currentPlayer = currentPlayer == 'x' ? 'o' : 'x';
		else {
			System.out.println(endgame);
			currentPlayer = ' ';
		}
		return new ResponseEntity<>(getbord(), HttpStatus.OK);
	}

	private String checkEndGame() {

		for (var i = 0; i < 3; i++) {

			if (checkSet(bord[i][0], bord[i][1], bord[i][2]))
				return "" + bord[i][0] + " Wins";
		}
		for (var i = 0; i < 3; i++) {

			if (checkSet(bord[0][i], bord[1][i], bord[2][i]))
				return "" + bord[0][i] + " Wins";
		}
		if (checkSet(bord[0][0], bord[1][1], bord[2][2]))
			return "" + bord[0][0] + " Wins";
		if (checkSet(bord[2][0], bord[1][1], bord[0][2]))
			return "" + bord[2][0] + " Wins";
		if (getbord().contains(" "))
			return null;
		else
			return "Cat's Game!";
	}

	private boolean checkSet(char c1, char c2, char c3) {
		return c1 != ' ' && c1 == c2 && c1 == c3;
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
			{ ' ', ' ', ' ' },
			{ ' ', ' ', ' ' },
			{ ' ', ' ', ' ' }
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
