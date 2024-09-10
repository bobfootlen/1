package guru.nnd.tutorial.java101;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Java101Application {


	@RequestMapping("/helloWorld")
	public String home() {
	  return "Hello Docker World";
	}
	public static void main(String[] args) {
		SpringApplication.run(Java101Application.class, args);
	}

}
