package telran.parking.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Configuration
@Getter
public class PaymentAnalyzerConfiguration {
	@Value("${app.pango.api.host:localhost}")
	String host;
@Value("${app.pango.api.port:8080}")
int port;
@Value("${app.pango.api.path:/pango-api/check-car/")
String pangoCheckApiPath ;
@Bean
RestTemplate getRestTemplate() {
	return new RestTemplate();
}

}
