package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.ServiceRecord;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByCarId(Long carId);

    List<ServiceRecord> findByMechanicId(Long mechanicId);

    List<ServiceRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT SUM(st.standardPrice) FROM ServiceRecord sr JOIN sr.serviceTypes st WHERE sr.car.id = :carId")
    Long getTotalServiceAmountForCar(Long carId);
}