package telran.parking.service;
import telran.parking.repo.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly=true)
public class ReportDataProviderServiceImpl implements ReportDataProviderService {
	@Autowired
	ReportDataRepo reportDataRepo;

	@Override
	public boolean isReportExists(String carNumber) {
		LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return reportDataRepo.existsReportForToday(carNumber, startOfDay, endOfDay);
	}


		
	
		
	}
	

