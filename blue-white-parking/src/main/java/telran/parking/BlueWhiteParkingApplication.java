package telran.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BlueWhiteParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlueWhiteParkingApplication.class, args);
	}

}
