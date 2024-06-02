package telran.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@ComponentScan(basePackages = {"telran.parking"})
public class ReportDataProviderAppl {

	public static void main(String[] args) {
		SpringApplication.run(ReportDataProviderAppl.class, args);
	}

}