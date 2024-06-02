package telran.parking.service;
import telran.parking.repo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.OwnerDto;
import telran.parking.dto.ReportDto;
import telran.parking.exceptions.OwnerNotFoundException;
import telran.parking.exceptions.ReportNotFoundException;
import telran.parking.model.OwnerData;
import telran.parking.model.ReportData;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly=true)
public class ReportDataProviderServiceImpl implements ReportDataProviderService {
	@Autowired
	OwnerDataRepo reportDataRepo;

	@Override
	public ReportDto getReportData(long ownerId) {
		ReportData reportData =reportDataRepo.findById(ownerId).orElseThrow(() -> new ReportNotFoundException());
		return reportData.buid();
	
		
	}
	

}
