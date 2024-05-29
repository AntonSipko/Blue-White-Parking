package telran.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
public class CameraDataProviderAppl {

	public static void main(String[] args) {
		SpringApplication.run(CameraDataProviderAppl.class, args);
	}

}
