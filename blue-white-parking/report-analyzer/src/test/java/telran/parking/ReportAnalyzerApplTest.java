package telran.parking;

import static org.junit.jupiter.api.Assertions.*;
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
    void testProcessParkingDto_allReportsExist() {
        // Given
        String[] carNumbers = {"ABC123", "XYZ789", "LMN456"};
        ParkingDto parkingDto = new ParkingDto(123L, carNumbers, LocalDateTime.now());
        when(reportExistsProviderClientService.reportExistsForToday(anyString())).thenReturn(true);

        // When
        reportAnalyzerAppl.processParkingDto(parkingDto);

        // Then
        verify(streamBridge, never()).send(anyString(), any(ParkingDto.class));
        verify(reportExistsProviderClientService, times(carNumbers.length)).reportExistsForToday(anyString());
    }

    @Test
    void testProcessParkingDto_someReportsDoNotExist() {
        // Given
        String[] carNumbers = {"ABC123", "XYZ789", "LMN456"};
        ParkingDto parkingDto = new ParkingDto(123L, carNumbers, LocalDateTime.now());
        when(reportExistsProviderClientService.reportExistsForToday("ABC123")).thenReturn(true);
        when(reportExistsProviderClientService.reportExistsForToday("XYZ789")).thenReturn(false);
        when(reportExistsProviderClientService.reportExistsForToday("LMN456")).thenReturn(false);

        // When
        reportAnalyzerAppl.processParkingDto(parkingDto);

        // Then
        verify(streamBridge, times(1)).send(eq("reportProducerBinding-out-0"), any(ParkingDto.class));
        verify(reportExistsProviderClientService, times(carNumbers.length)).reportExistsForToday(anyString());
    }

    @Test
    void testReportExistsForTodayForCarNumbers() {
        // Given
        String[] carNumbers = {"ABC123", "XYZ789", "LMN456"};
        when(reportExistsProviderClientService.reportExistsForToday("ABC123")).thenReturn(true);
        when(reportExistsProviderClientService.reportExistsForToday("XYZ789")).thenReturn(false);
        when(reportExistsProviderClientService.reportExistsForToday("LMN456")).thenReturn(false);

        // When
        String[] result = reportAnalyzerAppl.reportsNotExistsForTodayForCarNumbers(carNumbers);

        // Then
        assertArrayEquals(new String[]{"XYZ789", "LMN456"}, result);
        verify(reportExistsProviderClientService, times(carNumbers.length)).reportExistsForToday(anyString());
    }
}
