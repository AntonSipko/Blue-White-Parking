package telran.parking.model;
import jakarta.persistence.*;
import lombok.*;
import telran.parking.dto.*;
@Entity
@Table(name="all_cameras")
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Getter
@ToString

public class CameraData {
	@Id
	@Column(name="camera_id")
	long cameraId;
	@Column(name="camera_adress")
	String cameraAdress;
	@Column(name="adress_fine_price")
	double adressFinePrice;
	
	public CameraDto buid() {
		return new CameraDto(cameraId, cameraAdress, adressFinePrice);
		
	}


}
