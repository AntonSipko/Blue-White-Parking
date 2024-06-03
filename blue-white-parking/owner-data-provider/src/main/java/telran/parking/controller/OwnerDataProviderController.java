package telran.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import telran.parking.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.service.OwnerDataProviderService;


@RestController
@RequiredArgsConstructor
@Slf4j

public class OwnerDataProviderController {
	@Autowired
	OwnerDataProviderService ownerDataProviderService;
	
	 @GetMapping(UrlConstants.GET_OWNERS_URL + "{" + UrlConstants.OWNER_ID + "}")
	public OwnerDto getOwnerData(@PathVariable(UrlConstants.OWNER_ID)  long ownerId) {
		log.debug("Searching Data for Owner with ID : {}",ownerId);
		return ownerDataProviderService.getOwnerData(ownerId);
		
		
		
		
		
		
	}

	
	

}
