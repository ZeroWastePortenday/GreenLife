package potenday.zerowaste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZerowasteApplication {

	public static void main(String[] args) {

		System.out.println("hello world");
		System.out.println("ZerowasteApplication init");
		SpringApplication.run(ZerowasteApplication.class, args);

	}

}
