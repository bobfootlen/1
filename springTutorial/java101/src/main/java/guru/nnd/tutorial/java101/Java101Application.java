package guru.nnd.tutorial.java101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class Java101Application {

	@GetMapping("/add")
	public String add(@RequestParam("operands") double[] operands) {
		double value = 0;
		for (var operand : operands) {
			value += operand;
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
