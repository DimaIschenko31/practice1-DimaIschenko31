package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.model.Mechanic;
import ua.opnu.practice1_template.model.ServiceRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecord, Long> {
    List<ServiceRecord> findByCar(Car car);
    List<ServiceRecord> findByCarId(Long carId);
    List<ServiceRecord> findByMechanic(Mechanic mechanic);
    List<ServiceRecord> findByMechanicId(Long mechanicId);
    List<ServiceRecord> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT c, COUNT(sr) as count FROM Car c JOIN c.serviceRecords sr GROUP BY c ORDER BY count DESC")
    List<Object[]> findCarsWithMostServices();

    @Query("SELECT SUM(st.standardPrice) FROM ServiceRecord sr JOIN sr.serviceTypes st WHERE sr.car.id = :carId")
    BigDecimal getTotalServiceAmountForCar(@Param("carId") Long carId);

    @Query("SELECT COUNT(sr) FROM ServiceRecord sr WHERE sr.mechanic.id = :mechanicId")
    Long countServicesByMechanic(@Param("mechanicId") Long mechanicId);
}