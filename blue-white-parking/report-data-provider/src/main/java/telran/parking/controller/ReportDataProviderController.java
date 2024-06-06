package telran.parking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

public class ReportDataProviderController {
	@Autowired
	ReportDataProviderService reportDataProviderService;
	
	 @GetMapping(UrlConstants.GET_REPORTS_URL + "{" + UrlConstants.CAR_NUMBER + "}")
	public ResponseEntity<Boolean> getCameraData(@PathVariable(UrlConstants.CAR_NUMBER)  String carNumber) {
		log.debug("Searching Today's Data for Report with Car Number : {}",carNumber);
		boolean exist = reportDataProviderService.isReportExists(carNumber);
		return ResponseEntity.ok(exist);
		
		
		
		
		
		
	}

	
	

}
