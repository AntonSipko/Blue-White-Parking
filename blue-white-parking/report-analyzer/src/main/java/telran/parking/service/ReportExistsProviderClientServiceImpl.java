package telran.parking.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.HashMap;
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportExistsProviderClientServiceImpl implements ReportExistsProviderClientService {
	final RestTemplate restTemplate;
	final ServiceConfiguration serviceConfiguration;
	HashMap<String,Boolean>cache=new HashMap<>();
	@Override
	public boolean reportExistsForToday(String carNumber) {
		Boolean exists=cache.get(carNumber);
		if(exists == null) {
			log.debug("Report for car number {} doesn't exist in cache", carNumber);
			exists=serviceRequestAnswer(carNumber);
		}else {
			  log.debug("Report existence {} from cache", exists);
		}
		return exists;
	}
	private Boolean serviceRequestAnswer(String carNumber) {
		Boolean exists=false;
		ResponseEntity<Boolean> responseEntity;
		try {
            responseEntity = restTemplate.exchange(getUrl(carNumber), HttpMethod.GET, null, Boolean.class);
            if (responseEntity.getStatusCode().is4xxClientError()) {
                throw new Exception(responseEntity.getBody().toString());
            }
            exists = responseEntity.getBody();
            log.debug("Report existence value {}", exists);
            cache.put(carNumber, exists);
        } catch (Exception e) {
            log.error("Error at service request: {}", e.getMessage()); 
        }
        return exists;
	}
	private String getUrl(String carNumber) {
        String url = String.format("http://%s:%d%s%s", serviceConfiguration.getHost(), serviceConfiguration.getPort(),
                serviceConfiguration.getPath(), carNumber);
        log.debug("URL created is {}", url);
        return url;
    }
	

}
