package telran.parking.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;

@Configuration
@Getter
public class ServiceConfiguration {
	@Value("${app.report.provider.host:localhost}")
	String host;
@Value("${app.report.provider.port:8080}")
int port;
@Value("${app.report.provider.path:/reports/get/}")
String path;
@Bean
RestTemplate getRestTemplate() {
	return new RestTemplate();
}
	
	

}
