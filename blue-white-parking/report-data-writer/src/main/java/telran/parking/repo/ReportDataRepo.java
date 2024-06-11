package telran.parking.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import telran.parking.model.ReportData;

public interface ReportDataRepo extends JpaRepository<ReportData, String> {

}
