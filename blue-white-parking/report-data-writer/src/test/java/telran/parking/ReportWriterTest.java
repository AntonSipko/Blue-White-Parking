package telran.parking;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import telran.parkin.service.ReportWriterService;
import telran.parking.dto.ReportDto;
import telran.parking.dto.FineStatus;

@SpringBootTest
public class ReportWriterTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private ReportWriterService reportWriterService;

    @Test
    public void testReportWriterConsumer() {
        // Arrange
        ReportDto reportDto = new ReportDto(
            "ABC123",
            101L,
            "123 Camera Street",
            LocalDateTime.now(),
            1001L,
            "owner@example.com",
            150.0,
            FineStatus.UNPAID,
            "John Doe"
        );

        Consumer<ReportDto> reportWriterConsumer = (Consumer<ReportDto>) context.getBean("reportWriterComsumer");

        // Act
        reportWriterConsumer.accept(reportDto);

        // Assert
        verify(reportWriterService, times(1)).addReport(reportDto);
    }

    @Test
    public void testReportDtoProcessing() {
        // Arrange
        ReportDto reportDto = new ReportDto(
            "ABC123",
            101L,
            "123 Camera Street",
            LocalDateTime.now(),
            1001L,
            "owner@example.com",
            150.0,
            FineStatus.UNPAID,
            "John Doe"
        );

        ReportWriter reportWriter = context.getBean(ReportWriter.class);

        // Act
        reportWriter.reportDtoProscessing(reportDto);

        // Assert
        verify(reportWriterService, times(1)).addReport(reportDto);
    }
}
