package telran.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = {"telran.parking"})
public class OwnerDataProviderAppl {

	public static void main(String[] args) {
		SpringApplication.run(OwnerDataProviderAppl.class, args);
	}

}
