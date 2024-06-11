package telran.parking.service;
import telran.parking.dto.*;

public interface OwnerDataProviderService {
	OwnerDto getOwnerData(long ownerId);
	OwnerDto getOwnerDataByCarNumber(String carNumber);

}
