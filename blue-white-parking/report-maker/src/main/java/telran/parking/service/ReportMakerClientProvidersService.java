package telran.parking.service;
import telran.parking.dto.*;
public interface ReportMakerClientProvidersService {
	OwnerDto getOwnerData(String carNumber);
	CameraDto getCameraDto(String cameraNumber);
	
	

}
