package telran.parking.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import telran.parking.model.OwnerData;
import telran.parking.model.*;

public interface OwnerDataRepo extends JpaRepository<OwnerData,Long>  {

}
