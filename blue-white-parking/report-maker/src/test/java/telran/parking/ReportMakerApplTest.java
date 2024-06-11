package telran.parking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.stream.function.StreamBridge;

import telran.parking.dto.*;
import telran.parking.service.ReportMakerClientProvidersService;

class ReportMakerApplTest {

    @InjectMocks
    private ReportMakerAppl reportMakerAppl;

    @Mock
    private StreamBridge streamBridge;

    @Mock
    private ReportMakerClientProvidersService reportMakerClientProvidersService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessParkingDto() {
        // Given
        ParkingDto parkingDto = new ParkingDto(123L, new String[]{"ABC123", "XYZ789"}, LocalDateTime.now());
        CameraDto cameraDto = new CameraDto(123L, "Main St", 100);
        OwnerDto ownerDto1 = new OwnerDto(1L, "ABC123", "John Doe", "john@example.com");
        OwnerDto ownerDto2 = new OwnerDto(2L, "XYZ789", "Jane Doe", "jane@example.com");

        when(reportMakerClientProvidersService.getCameraDto(123L)).thenReturn(cameraDto);
        when(reportMakerClientProvidersService.getOwnerDataByCarNumber("ABC123")).thenReturn(ownerDto1);
        when(reportMakerClientProvidersService.getOwnerDataByCarNumber("XYZ789")).thenReturn(ownerDto2);

        // When
        reportMakerAppl.processParkingDto(parkingDto);

        // Then
        verify(reportMakerClientProvidersService).getCameraDto(123L);
        verify(reportMakerClientProvidersService).getOwnerDataByCarNumber("ABC123");
        verify(reportMakerClientProvidersService).getOwnerDataByCarNumber("XYZ789");
        verify(streamBridge, times(2)).send(eq("reportProducerBindingName-out-0"), any(ReportDto.class));
    }

    @Test
    void testSendReports() {
        // Given
        CameraDto cameraDto = new CameraDto(123L, "Main St", 100);
        OwnerDto ownerDto1 = new OwnerDto(1L, "ABC123", "John Doe", "john@example.com");
        OwnerDto ownerDto2 = new OwnerDto(2L, "XYZ789", "Jane Doe", "jane@example.com");
        ParkingDto parkingDto = new ParkingDto(123L, new String[]{"ABC123", "XYZ789"}, LocalDateTime.now());

        // When
        reportMakerAppl.sendReports(cameraDto, new OwnerDto[]{ownerDto1, ownerDto2}, parkingDto);

        // Then
        verify(streamBridge, times(2)).send(eq("reportProducerBindingName-out-0"), any(ReportDto.class));
    }
}
