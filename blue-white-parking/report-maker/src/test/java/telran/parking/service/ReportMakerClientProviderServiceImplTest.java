package telran.parking.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import telran.parking.dto.CameraDto;
import telran.parking.dto.OwnerDto;

public class ReportMakerClientProviderServiceImplTest {

    @InjectMocks
    private ReportMakerClientProviderServiceImpl reportMakerClientProviderServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ServiceConfiguration serviceConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock the service configuration methods
        when(serviceConfig.getHost()).thenReturn("localhost");
        when(serviceConfig.getPort()).thenReturn(8080);
        when(serviceConfig.getOwnersPathByCarNumber()).thenReturn("/owners/");
        when(serviceConfig.getCamerasPath()).thenReturn("/cameras/");
    }

    @Test
    void testGetOwnerDataByCarNumber() {
        // Given
        OwnerDto ownerDto = new OwnerDto(1L, "ABC123", "John Doe", "john@example.com");
        ResponseEntity<OwnerDto> responseEntity = ResponseEntity.ok(ownerDto);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(OwnerDto.class)))
                .thenReturn(responseEntity);

        // When
        OwnerDto result = reportMakerClientProviderServiceImpl.getOwnerDataByCarNumber("ABC123");

        // Then
        assertEquals(ownerDto, result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(OwnerDto.class));
    }

    @Test
    void testGetCameraDto() {
        // Given
        CameraDto cameraDto = new CameraDto(123L, "Main St", 100);
        ResponseEntity<CameraDto> responseEntity = ResponseEntity.ok(cameraDto);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(CameraDto.class)))
                .thenReturn(responseEntity);

        // When
        CameraDto result = reportMakerClientProviderServiceImpl.getCameraDto(123L);

        // Then
        assertEquals(cameraDto, result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(CameraDto.class));
    }

    @Test
    void testGetOwnerDataByCarNumber_NotFound() {
        // Given
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(OwnerDto.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // When
        OwnerDto result = reportMakerClientProviderServiceImpl.getOwnerDataByCarNumber("ABC123");

        // Then
        assertNull(result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(OwnerDto.class));
    }

    @Test
    void testGetCameraDto_NotFound() {
        // Given
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(CameraDto.class)))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // When
        CameraDto result = reportMakerClientProviderServiceImpl.getCameraDto(123L);

        // Then
        assertNull(result);
        verify(restTemplate, times(1)).exchange(anyString(), eq(HttpMethod.GET), isNull(), eq(CameraDto.class));
    }
}
