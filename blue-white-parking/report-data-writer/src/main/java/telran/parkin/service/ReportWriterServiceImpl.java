package telran.parkin.service;

import telran.parking.dto.ReportDto;
import telran.parking.model.ReportData;
import telran.parking.repo.ReportDataRepo;

public class ReportWriterServiceImpl implements ReportWriterService {

ReportDataRepo reportRepo;
	@Override
	public void addReport(ReportDto reportDto) {
		ReportData reportData=reportRepo.save(new ReportData(reportDto));
		
		
		
	}
	
}