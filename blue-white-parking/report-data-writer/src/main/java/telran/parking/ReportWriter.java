package telran.parking;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import telran.parkin.service.ReportWriterService;
import telran.parking.dto.ReportDto;

@SpringBootApplication
@Slf4j
public class ReportWriter {
	@Autowired
	ReportWriterService reportWriterService;

	public static void main(String[] args) {
		SpringApplication.run(ReportWriter.class, args);
		
		}
	@Bean
	Consumer<ReportDto>reportWriterComsumer(){
		return reportDto-> reportDtoProscessing(reportDto);

	}
	void reportDtoProscessing(ReportDto reportDto) {
		log.debug("recieved reportDto :  {}",reportDto);
		reportWriterService.addReport(reportDto);
		log.debug("report was added");
		
	}

}
