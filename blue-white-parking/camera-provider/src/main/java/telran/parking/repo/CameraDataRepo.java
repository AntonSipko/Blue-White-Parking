package telran.parking.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import telran.parking.model.CameraData;
import telran.parking.model.*;

public interface CameraDataRepo extends JpaRepository<CameraData,Long>  {

}
