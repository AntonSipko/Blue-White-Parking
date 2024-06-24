package telran.parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import telran.parking.dto.ParkingDto;

@Configuration
public class TestConfig {

    @Bean
    public AtomicReference<LocalDateTime> lastDataReceivedTime() {
        return new AtomicReference<>(LocalDateTime.now());
    }

    @Bean
    public Duration checkingDuration(@Value("${app.camera.checker.duration:PT0.1S}") String duration) {
        return Duration.parse(duration);
    }

    @Bean
    public CameraLifeCheckerAppl cameraLifeCheckerAppl(AtomicReference<LocalDateTime> lastDataReceivedTime, Duration checkingDuration) {
        return new CameraLifeCheckerAppl(lastDataReceivedTime, checkingDuration);
    }

    @Bean
    public Consumer<ParkingDto> dataProcessing(CameraLifeCheckerAppl cameraLifeCheckerAppl) {
        return cameraLifeCheckerAppl.dataProccessing();
    }
}
