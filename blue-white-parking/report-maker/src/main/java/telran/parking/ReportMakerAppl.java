package telran.parking;
import telran.parking.dto.*;
import java.util.Arrays;
import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.CameraDto;
import telran.parking.dto.OwnerDto;
import telran.parking.dto.ParkingDto;
import telran.parking.service.ReportMakerClientProvidersService;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ReportMakerAppl {
	StreamBridge streamBridge;
	ReportMakerClientProvidersService reportMakerClientProvidersService;
	String producerBindingName="reportProducerBindingName-out-0";
	public static void main(String[] args) {
		SpringApplication.run(ReportMakerAppl.class, args);

	}
	@Bean
	Consumer<ParkingDto>reportMakerConsumer(){
		return parkingDto->processParkingDto(parkingDto);
		
	}
	 void processParkingDto(ParkingDto parkingDto) {
		log.debug("Received ParkingDto: {}", parkingDto);
		log.debug("requesting camera data for cameraId :{}",parkingDto.cameraId());
		CameraDto cameraDto=reportMakerClientProvidersService.getCameraDto(parkingDto.cameraId());
		log.debug("cameraDto{}",cameraDto);
		String[]carNumbers=parkingDto.carNumbers();
		log.debug("Injected Car Numbers From parkingDto: {}", carNumbers);
		OwnerDto[]ownerDtos=Arrays.stream(carNumbers).map(carNumber->reportMakerClientProvidersService.getOwnerDataByCarNumber(carNumber)).toArray(OwnerDto[]::new);
		sendReports(cameraDto,ownerDtos,parkingDto);
		
		
		
	}
	void sendReports(CameraDto cameraDto, OwnerDto[] ownerDtos, ParkingDto parkingDto) {
		ReportDto[]reports=Arrays.stream(ownerDtos).map(ownerDto->new ReportDto(ownerDto.carNumber(),cameraDto.cameraId(),cameraDto.cameraAdress(),parkingDto.timeStamp(),ownerDto.ownerId(),ownerDto.ownerEmail(),cameraDto.adressFinePrice(),FineStatus.UNPAID,ownerDto.name())).toArray(ReportDto[]::new);
		Arrays.stream(reports).forEach(report->streamBridge.send(producerBindingName, report));
		
	}

}
