package telran.parking.service;
import telran.parking.dto.*;
public interface ReportMakerClientProvidersService {
	OwnerDto getOwnerDataByCarNumber(String carNumber);
	CameraDto getCameraDto(long cameraId);
	
	

}
