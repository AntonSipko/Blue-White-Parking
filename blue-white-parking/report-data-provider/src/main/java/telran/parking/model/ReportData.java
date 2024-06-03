package telran.parking.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import telran.parking.dto.*;
@Entity
@Table(name="all_reports")
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Getter
@ToString

public class ReportData {
	@Id
	@Column(name="car_number")
	String carNumber;
	@Column(name="camera_id")
	long cameraId;
	@Column(name="camera_adress")
	String cameraAdress;
	@Column(name="adress_fine_price")
	double adressFinePrice;
	@Enumerated(EnumType.STRING)
	@Setter
	FineStatus fineStatus;
	@Temporal(TemporalType.TIMESTAMP)
	LocalDateTime timestamp;
	@Column(name="owner_id")
	long ownerId;
	@Column(name="owner_name")
	String ownerName;
	@Column(name="owner_email")
	String ownerEmail;
	
	
	public ReportDto buid() {
		return new ReportDto(carNumber,cameraId,cameraAdress,timestamp,ownerId,ownerEmail,adressFinePrice,fineStatus,ownerName);
		
	}


}
