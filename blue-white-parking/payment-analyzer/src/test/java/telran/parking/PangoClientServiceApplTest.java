package telran.parking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import telran.parking.configuration.PaymentAnalyzerConfiguration;
import telran.parking.service.PangoClientServiceAppl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class PangoClientServiceApplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PaymentAnalyzerConfiguration config;

    @InjectMocks
    private PangoClientServiceAppl pangoClientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(config.getHost()).thenReturn("localhost");
        when(config.getPort()).thenReturn(8080);
        when(config.getPangoCheckApiPath()).thenReturn("/pango-api/check-car/");
    }

    @Test
    public void testGetPangoAnswerSuccess() {
        Long carNumber = 123456L;
        String expectedResponse = "some time string";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(expectedResponse, HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(responseEntity);

        String result = pangoClientService.getPangoAnswer(carNumber);

        assertEquals(expectedResponse, result);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.GET), any(), eq(String.class));
    }

    @Test
    public void testGetPangoAnswerClientError() {
        Long carNumber = 123456L;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Client Error", HttpStatus.BAD_REQUEST);

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(responseEntity);

        String result = pangoClientService.getPangoAnswer(carNumber);

        assertEquals(null, result);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.GET), any(), eq(String.class));
    }

    @Test
    public void testGetPangoAnswerException() {
        Long carNumber = 123456L;

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenThrow(new RuntimeException("Some error"));

        String result = pangoClientService.getPangoAnswer(carNumber);

        assertEquals(null, result);
        verify(restTemplate, times(1)).exchange(any(String.class), eq(HttpMethod.GET), any(), eq(String.class));
    }
}
