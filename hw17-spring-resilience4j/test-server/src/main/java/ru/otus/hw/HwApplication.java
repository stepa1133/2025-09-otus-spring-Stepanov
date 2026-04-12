package ru.otus.hw;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.otus.hw.service.TimeService;

@SpringBootApplication
@EnableFeignClients
public class HwApplication {
	public static void main(String[] args) {
		SpringApplication.run(HwApplication.class, args);
	}

}
