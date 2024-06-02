package telran.parking.model;
import jakarta.persistence.*;
import lombok.*;
import telran.parking.dto.*;
@Entity
@Table(name="all_owners")
@NoArgsConstructor
@AllArgsConstructor(access=AccessLevel.PRIVATE)
@Getter
@ToString

public class OwnerData {
	@Id
	@Column(name="owner_id")
	long ownerId;
	@Column(name="car_number")
	String carNumber;
	@Column(name="owner_name")
	String ownerName;
	@Column(name="owner_email")
	String ownerEmail;
	
	public OwnerDto buid() {
		return new OwnerDto(ownerId, carNumber, ownerName,ownerEmail);
		
	}


}
