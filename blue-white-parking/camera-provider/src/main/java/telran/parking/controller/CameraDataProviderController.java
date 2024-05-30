package telran.parking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import telran.parking.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.service.CameraDataProviderService;


@RestController
@RequiredArgsConstructor
@Slf4j

public class CameraDataProviderController {
	CameraDataProviderService cameraDataProviderService;
	
	@GetMapping(UrlConstants.GET_CAMERAS_URL+"{"+UrlConstants.CAMERA_ID+"}")
	public CameraDto getCameraData(@PathVariable(UrlConstants.CAMERA_ID)  long cameraId) {
		log.debug("camera data for camera with id {}",cameraId);
		return cameraDataProviderService.getCameraData(cameraId);
		
		
		
		
		
		
	}

	
	

}