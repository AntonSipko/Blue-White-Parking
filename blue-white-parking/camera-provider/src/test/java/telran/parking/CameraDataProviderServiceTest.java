package telran.parking;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import telran.parking.dto.*;
import telran.parking.repo.CameraDataRepo;
import telran.parking.service.CameraDtoProviderService;
import telran.parking.exceptions.*;
@SpringBootTest
class CameraDataProviderServiceTest {
	@Autowired
    private CameraDtoProviderService cameraDtoProviderService;
	
	
	@Test
	void testGetCameraData() {
        CameraDto expected = new CameraDto(12345L, "Herzel 6 Tel-Aviv", 100.0);
        CameraDto actual = cameraDtoProviderService.getCameraData(12345L);
        assertEquals(expected, actual);
        
    }

    @Test
    void testGetCameraDataNotFound() {
        assertThrows(CameraNotFoundException.class, () -> {
            cameraDtoProviderService.getCameraData(999L);
        });
    }

}
