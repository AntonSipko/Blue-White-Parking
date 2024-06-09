package telran.parking.service;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.CameraDto;
import telran.parking.dto.OwnerDto;
@Service
@RequiredArgsConstructor
@Slf4j
public class ReportMakerClientProviderServiceImpl implements ReportMakerClientProvidersService {
	RestTemplate restTemplate;
	ServiceConfiguration serviceConfig;
	@Override
	public OwnerDto getOwnerData(String carNumber) {
		OwnerDto ownerDto=null;
		try {
		ResponseEntity<OwnerDto> ownerDtoEntity=restTemplate.exchange(getUrl(serviceConfig.ownersPath, carNumber), HttpMethod.GET,null,OwnerDto.class);
		 if (ownerDtoEntity.getStatusCode().is4xxClientError()) {
             throw new Exception(ownerDtoEntity.getBody().toString());
         }
		  ownerDto=ownerDtoEntity.getBody();
		 log.debug("recieved OwnerDto {}",ownerDtoEntity);
		} catch (Exception e) {
            log.error("Error at service request: {}", e.getMessage()); 
        }
		return ownerDto;
		
	}

	@Override
	public CameraDto getCameraDto(String cameraId) {
		CameraDto cameraDto=null;
		try {
		ResponseEntity<CameraDto> cameraDtoEntity=restTemplate.exchange(getUrl(serviceConfig.camerasPath, cameraId), HttpMethod.GET,null,CameraDto.class);
		 if (cameraDtoEntity.getStatusCode().is4xxClientError()) {
             throw new Exception(cameraDtoEntity.getBody().toString());
         }
		 cameraDto=cameraDtoEntity.getBody();
		 log.debug("recieved CameraDto {}",cameraDtoEntity);
		} catch (Exception e) {
            log.error("Error at service request: {}", e.getMessage()); 
        }
		return cameraDto;
	}
	private String getUrl(String path,String number) {
        String url = String.format("http://%s:%d%s%s", serviceConfig.getHost(), serviceConfig.getPort(),
                path, number);
        log.debug("URL created is {}", url);
        return url;
    }

}
