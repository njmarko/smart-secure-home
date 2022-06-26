package kiber.bezbednjaci;

import kiber.bezbednjaci.logging.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
@RequiredArgsConstructor
public class SecureHomeApplication {
	private final LogService logService;

	public static void main(String[] args) {
		SpringApplication.run(SecureHomeApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner() {
		return args -> {
			IntStream.range(1, 51).forEach(x -> logService.info("hello from smart home"));
		};
	}

}
