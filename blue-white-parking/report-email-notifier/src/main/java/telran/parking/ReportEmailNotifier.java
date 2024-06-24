package telran.parking;

import java.time.Instant;
import java.time.*;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.parking.dto.ReportDto;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class ReportEmailNotifier {
    @Value("${app.mail.notifier.subject:Report For Illegal Parking on street }")
    private String subject;
    final JavaMailSender mailSender;

    public static void main(String[] args) {
        SpringApplication.run(ReportEmailNotifier.class, args);
    }

    Consumer<ReportDto> ReportDtoConsumer() {
        return reportDto -> reportSending(reportDto);
    }

    void reportSending(ReportDto reportDto) {
        log.debug("received reportData : {}", reportDto);
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(reportDto.ownerEmail());
        smm.setSubject(getSubject(reportDto));
        smm.setText(getEmailText(reportDto));
        mailSender.send(smm);
        log.debug("mail has been sent");
    }

    private String getEmailText(ReportDto reportDto) {
        String text = String.format("Hello %s,"
                + " According to our Parking Camera with id: %d on street %s, You have been parking Illegally at the time: %s, On Date : %s"
                + " Your fine price is %.2f", 
                reportDto.ownerName(), 
                reportDto.cameraId(), 
                reportDto.cameraAdress(), 
                getTime(reportDto.timeStamp()),
                getDate(reportDto.timeStamp()),// Convert LocalDateTime to String
                reportDto.adressFinePrice());
        return text;
    }

     LocalDate getDate(LocalDateTime timeStamp) {
		
		return timeStamp.toLocalDate();
	}

	LocalTime getTime(LocalDateTime timeStamp) {
		
		return timeStamp.toLocalTime();
	}

	private String getSubject(ReportDto reportDto) {
        String subjectForMail = subject + reportDto.cameraAdress();
        return subjectForMail;
    }
}
