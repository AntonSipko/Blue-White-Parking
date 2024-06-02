package telran.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import telran.parking.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.service.ReportDataProviderService;


@RestController
@RequiredArgsConstructor
@Slf4j

public class CameraDataProviderController {
	@Autowired
	ReportDataProviderService reportDataProviderService;
	
	 @GetMapping(UrlConstants.GET_REPORTS_URL + "{" + UrlConstants.OWNER_ID + "}")
	public OwnerDto getCameraData(@PathVariable(UrlConstants.OWNER_ID)  long ownerId) {
		log.debug("Searching Data for Owner with ID : {}",ownerId);
		return reportDataProviderService.getReportData(ownerId);
		
		
		
		
		
		
	}

	
	

}
