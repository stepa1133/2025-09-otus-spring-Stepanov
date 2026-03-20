package ru.otus.hw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {


	//http://127.0.0.1:8081/actuator
	//http://127.0.0.1:8081/actuator/health
	//http://127.0.0.1:8081/actuator/logfile
	//http://127.0.0.1:8081/actuator/mappings

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.printf("Чтобы перейти на страницу сайта открывай: %n%s%n",
				"http://localhost:8081/book");
	}

}
