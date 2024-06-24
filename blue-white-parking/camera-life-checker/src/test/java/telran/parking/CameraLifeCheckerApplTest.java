package telran.parking;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import telran.parking.dto.ParkingDto;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class CameraLifeCheckerApplTest {

    @Autowired
    private CameraLifeCheckerAppl cameraLifeCheckerAppl;

    @Autowired
    private Duration checkingDuration;

    @BeforeEach
    void setUp() {
        // Additional setup if required
    }

    @Test
    void testDataProcessing() {
        ParkingDto parkingDto = new ParkingDto(1L, new String[]{"AB123CD", "EF456GH"}, LocalDateTime.now());
        Consumer<ParkingDto> dataProcessing = cameraLifeCheckerAppl.dataProccessing();

        dataProcessing.accept(parkingDto);

        LocalDateTime lastReceivedTime = cameraLifeCheckerAppl.getLastDataReceivedTime().get();
        assertNotNull(lastReceivedTime);
        assertTrue(Duration.between(lastReceivedTime, LocalDateTime.now()).compareTo(checkingDuration) <= 0);
    }

    @Test
    void testCheckDataReceivedWithinInterval() {
        ParkingDto parkingDto = new ParkingDto(1L, new String[]{"AB123CD", "EF456GH"}, LocalDateTime.now());
        cameraLifeCheckerAppl.updateTime(parkingDto);

        assertDoesNotThrow(() -> cameraLifeCheckerAppl.checkDataReceived());
    }

    @Test
    void testCheckDataReceivedExceedingInterval() throws InterruptedException {
        ParkingDto parkingDto = new ParkingDto(1L, new String[]{"AB123CD", "EF456GH"}, LocalDateTime.now());
        cameraLifeCheckerAppl.updateTime(parkingDto);

        // Sleep to exceed the checking interval
        Thread.sleep(checkingDuration.toMillis() + 10);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> cameraLifeCheckerAppl.checkDataReceived());
        assertEquals("No data received from the camera imitator within the expected interval.", exception.getMessage());
    }
}
