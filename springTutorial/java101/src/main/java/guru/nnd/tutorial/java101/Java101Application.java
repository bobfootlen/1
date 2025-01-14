package guru.nnd.tutorial.java101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Java101Application {

	@GetMapping("/tictac/board")
	public String getboard() {
		return boardToString(board);
	}

	@PutMapping("/tictac/board")
	public String resetboard() {
		for (var i = 0; i < 3; i++) {
			for (var j = 0; j < 3; j++) {
				board[i][j] = ' ';
			}
		}
		if(' '==currentPlayer){
			currentPlayer = Math.floor(Math.random()*100) % 2 == 1 ? 'x':'o';
		}
		return getboard();
	}

	@PostMapping("/tictac/board")
	public ResponseEntity<String> recordmove(String player, int column, int row) {
		if (currentPlayer != player.charAt(0)) {
			return new ResponseEntity<>("It's not your turn.", HttpStatus.BAD_REQUEST);
		}
		if (column > 2 || column < 0 || row > 2 || row < 0) {
			return new ResponseEntity<>("Row or Column Out of Bounds", HttpStatus.BAD_REQUEST);
		}
		if (board[row][column] != ' ') {
			return new ResponseEntity<>("Space Already Occupied", HttpStatus.BAD_REQUEST);
		}
		board[row][column] = currentPlayer;
		var endgame = checkEndGame();
		if (endgame == null)
			currentPlayer = currentPlayer == 'x' ? 'o' : 'x';
		else {
			System.out.println(endgame);
			currentPlayer = ' ';
		}
		return new ResponseEntity<>(getboard(), HttpStatus.OK);
	}

	private String checkEndGame() {

		for (var i = 0; i < 3; i++) {

			if (checkSet(board[i][0], board[i][1], board[i][2]))
				return "" + board[i][0] + " Wins";
		}
		for (var i = 0; i < 3; i++) {

			if (checkSet(board[0][i], board[1][i], board[2][i]))
				return "" + board[0][i] + " Wins";
		}
		if (checkSet(board[0][0], board[1][1], board[2][2]))
			return "" + board[0][0] + " Wins";
		if (checkSet(board[2][0], board[1][1], board[0][2]))
			return "" + board[2][0] + " Wins";
		if (getboard().contains(" "))
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

	private String boardToString(char[][] board) {
		StringBuilder builder = new StringBuilder(11);
		builder.append('"');
		builder.append(board[0]);
		builder.append(board[1]);
		builder.append(board[2]);
		builder.append('"');
		return builder.toString();
	}

	private char[][] board = {
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
