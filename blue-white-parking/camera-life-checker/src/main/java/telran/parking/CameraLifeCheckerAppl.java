package telran.parking;

import java.util.function.Consumer;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.ParkingDto;

@SpringBootApplication
@Slf4j
public class CameraLifeCheckerAppl {
    private final AtomicReference<LocalDateTime> lastDataReceivedTime;
    private final Duration checkingDuration;

    public CameraLifeCheckerAppl(
            AtomicReference<LocalDateTime> lastDataReceivedTime, 
            @Value("${app.camera.checker.duration:PT0.1S}") Duration checkingDuration) {
        this.lastDataReceivedTime = lastDataReceivedTime;
        this.checkingDuration = checkingDuration;
    }

    public static void main(String[] args) {
        SpringApplication.run(CameraLifeCheckerAppl.class, args);
    }

    @Bean
    public Consumer<ParkingDto> dataProccessing() {
        return this::updateTime;
    }

    void updateTime(ParkingDto parkingDto) {
        lastDataReceivedTime.set(LocalDateTime.now());
        log.debug("Data received: {}", parkingDto);
    }

    @Scheduled(fixedRateString = "${camera.checker.interval:100}")
    public void checkDataReceived() {
        LocalDateTime lastReceived = lastDataReceivedTime.get();
        if (Duration.between(lastReceived, LocalDateTime.now()).compareTo(checkingDuration) > 0) {
            throw new IllegalStateException("No data received from the camera imitator within the expected interval.");
        }
    }

    public AtomicReference<LocalDateTime> getLastDataReceivedTime() {
        return lastDataReceivedTime;
    }
}
