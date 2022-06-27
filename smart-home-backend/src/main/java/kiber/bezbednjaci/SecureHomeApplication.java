package kiber.bezbednjaci;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SecureHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureHomeApplication.class, args);
	}

}
