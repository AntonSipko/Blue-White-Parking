package telran.parking.repo;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import telran.parking.model.ReportData;

public interface ReportDataRepo extends JpaRepository<ReportData, String> {

    @Query("SELECT COUNT(r) > 0 FROM ReportData r WHERE r.carNumber = :carNumber AND r.timeStamp BETWEEN :startOfDay AND :endOfDay")
    boolean existsReportForToday(@Param("carNumber") String carNumber,
                                 @Param("startOfDay") LocalDateTime startOfDay,
                                 @Param("endOfDay") LocalDateTime endOfDay);
}
