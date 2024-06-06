package telran.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.function.Consumer;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.ParkingDto;
import telran.parking.service.ReportExistsProviderClientService;
@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ReportAnalyzerAppl {
	final ReportExistsProviderClientService reportExistsProviderClientService;
	final StreamBridge streamBridge;
	String producerBindingName= "reportProducerBinding-out-0";
	public static void main(String[] args) {
		SpringApplication.run(ReportAnalyzerAppl.class, args);
	}
		 @Bean
		 Consumer<ParkingDto>reportAnalyzerConsumer(){
			 return parkingDto ->processParkingDto(parkingDto);
		 }
		void processParkingDto(ParkingDto parkingDto) {
			log.debug("Received ParkingDto: {}", parkingDto);
			boolean reportExists=reportExistsProviderClientService.reportExistsForToday(parkingDto.carNumber());
			if(!reportExists) {
				log.debug("Report doesn't exists for today,sending parkingDto");
				streamBridge.send(producerBindingName, parkingDto);
			}
			
			
		}
			
		}
		
		
		
		

	


