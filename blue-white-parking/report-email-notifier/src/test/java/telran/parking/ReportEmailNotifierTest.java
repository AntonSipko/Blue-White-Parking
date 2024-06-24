package telran.parking;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import telran.parking.dto.FineStatus;
import telran.parking.dto.ReportDto;

@ExtendWith(MockitoExtension.class)
public class ReportEmailNotifierTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private ReportEmailNotifier reportEmailNotifier;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Value("${app.mail.notifier.subject:Report For Illegal Parking on street }")
    private String subject;

    private ReportDto reportDto;

    @BeforeEach
    void setUp() {
        reportDto = new ReportDto(
                "AB123CD",                // carNumber
                123,                      // cameraId
                "Main St",                // cameraAdress
                LocalDateTime.now(),      // timeStamp
                456,                      // ownerId
                "owner@example.com",      // ownerEmail
                100.0,                    // adressFinePrice
                FineStatus.UNPAID,        // fineStatus
                "John Doe"                // ownerName
        );
    }

    @Test
    void testReportSending() {
        
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));
        reportEmailNotifier.reportSending(reportDto);
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assert capturedMessage.getTo()[0].equals(reportDto.ownerEmail());
        assert capturedMessage.getSubject().equals(subject + reportDto.cameraAdress());
        String expectedText = String.format("Hello %s,"
                + " According to our Parking Camera with id: %d on street %s, You have been parking Illegally at the time: %s, On Date : %s"
                + " Your fine price is %.2f", 
                reportDto.ownerName(), 
                reportDto.cameraId(), 
                reportDto.cameraAdress(), 
                reportEmailNotifier.getTime(reportDto.timeStamp()),
                reportEmailNotifier.getDate(reportDto.timeStamp()),// Convert LocalDateTime to String
                reportDto.adressFinePrice());
        assert capturedMessage.getText().equals(expectedText);
        System.out.println(expectedText);
    }
}
