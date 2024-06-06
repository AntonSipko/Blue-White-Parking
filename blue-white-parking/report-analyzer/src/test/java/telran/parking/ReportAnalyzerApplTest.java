package telran.parking;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.stream.function.StreamBridge;

import telran.parking.dto.ParkingDto;
import telran.parking.service.ReportExistsProviderClientService;

class ReportAnalyzerApplTest {

    @InjectMocks
    private ReportAnalyzerAppl reportAnalyzerAppl;

    @Mock
    private ReportExistsProviderClientService reportExistsProviderClientService;

    @Mock
    private StreamBridge streamBridge;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessParkingDto_reportExists() {
        // Given
        ParkingDto parkingDto = new ParkingDto(123L, "A1", "25-365-28", LocalDateTime.now());
        when(reportExistsProviderClientService.reportExistsForToday(anyString())).thenReturn(true);

        // When
        reportAnalyzerAppl.processParkingDto(parkingDto);

        // Then
        verify(streamBridge, never()).send(anyString(), any(ParkingDto.class));
    }

    @Test
    void testProcessParkingDto_reportDoesNotExist() {
        // Given
        ParkingDto parkingDto = new ParkingDto(123L, "A1", "25-365-28", LocalDateTime.now());
        when(reportExistsProviderClientService.reportExistsForToday(anyString())).thenReturn(false);

        // When
        reportAnalyzerAppl.processParkingDto(parkingDto);

        // Then
        verify(streamBridge, times(1)).send(eq("reportProducerBinding-out-0"), eq(parkingDto));
    }
}
