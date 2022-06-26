package kiber.bezbednjaci;

import kiber.bezbednjaci.runnables.DefaultRunnable;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class DevicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevicesApplication.class, args);
	}

	@Bean
	public ApplicationRunner test() {
		return args -> {
			fireDeviceThreads();
		};
	}
	
	private void fireDeviceThreads() {
		var df = new DefaultRunnable();

		Thread t1 = new Thread(df);
		Thread t2 = new Thread(df);
		Thread t3 = new Thread(df);
		Thread t4 = new Thread(df);
		Thread t5 = new Thread(df);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}

}
