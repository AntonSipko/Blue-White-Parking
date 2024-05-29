package telran.parking.service;
import telran.parking.repo.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.CameraDto;
import telran.parking.exceptions.CameraNotFoundException;
import telran.parking.model.CameraData;
@Service
@RequiredArgsConstructor
@Slf4j
public class CameraDtoProviderServiceImpl implements CameraDtoProviderService {
	CameraDataRepo cameraDataRepo;

	@Override
	public CameraDto getCameraData(long cameraId) {
		CameraData cameraData= cameraDataRepo.findById(cameraId).orElseThrow(() -> new CameraNotFoundException());
		return cameraData.buid();
	
		
	}
	

}
