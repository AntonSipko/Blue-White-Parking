package telran.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

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
	String producerBindingName= "reportAnalyzerProducerBinding-out-0";
	public static void main(String[] args) {
		SpringApplication.run(ReportAnalyzerAppl.class, args);
	}
		 @Bean
		 Consumer<ParkingDto>reportAnalyzerConsumer(){
			 return parkingDto ->processParkingDto(parkingDto);
		 }
		void processParkingDto(ParkingDto parkingDto) {
			log.debug("Received ParkingDto: {}", parkingDto);
			String[]arrayToSend=reportsNotExistsForTodayForCarNumbers(parkingDto.carNumbers());
			if(arrayToSend.length==0) {
				log.debug("All of this cars have the Reports for Today");
			}else {
				log.debug("Sending Dto For a new Report");
				ParkingDto dtoForSending=new ParkingDto(parkingDto.cameraId(), arrayToSend, parkingDto.timeStamp());
				streamBridge.send(producerBindingName, dtoForSending);

			}
			
		}
		public String[] reportsNotExistsForTodayForCarNumbers(String[]carNumers)  {
			return  (String[]) Arrays.stream(carNumers).filter(carNumber->!reportExistsProviderClientService.reportExistsForToday(carNumber)).toArray(String[]::new);
			
		}
		
		
		
		
}
	


