package telran.parking.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.parking.model.OwnerData;
import telran.parking.model.*;

public interface OwnerDataRepo extends JpaRepository<OwnerData,Long>  {
	@Query("SELECT o FROM OwnerData WHERE o.carNumber=:carNumber")
	OwnerData getOwnerDataByCarNumber(@Param(value = "carNumber") String carNumber);

}
