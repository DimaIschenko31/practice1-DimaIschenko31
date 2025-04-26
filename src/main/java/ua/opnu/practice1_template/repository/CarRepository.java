package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByClientId(Long clientId);

    @Query("SELECT c FROM Car c JOIN c.serviceRecords sr GROUP BY c ORDER BY COUNT(sr) DESC")
    List<Car> findCarsWithMostServiceRecords();
}