package telran.parking.service;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.configuration.PaymentAnalyzerConfiguration;
@Service
@RequiredArgsConstructor
@Slf4j
public class PangoClientServiceAppl implements PangoClientService {
	RestTemplate restTemplate;
	PaymentAnalyzerConfiguration config;

	@Override
	public String getPangoAnswer(Long carNumber) {
		String answer=null;
		try {
		ResponseEntity<String> timeStringEntity=restTemplate.exchange(getUrl(carNumber.toString()), HttpMethod.GET,null,String.class);
		 if (timeStringEntity.getStatusCode().is4xxClientError()) {
             throw new Exception(timeStringEntity.getBody().toString());
         }
		  answer=timeStringEntity.getBody();
		 log.debug("recieved PangoTime {}",answer);
		} catch (Exception e) {
            log.error("Error at service request,Data is: {}", e.getMessage()); 
        }
		return answer;
	}

	private String getUrl(String number) {
        String url = String.format("http://%s:%d%s%s",config.getHost(), config.getPort(),
                config.getPangoCheckApiPath(), number);
        log.debug("URL created is {}", url);
        return url;
    }

}
