package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.Car;
import ua.opnu.practice1_template.model.Client;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByClient(Client client);
    List<Car> findByClientId(Long clientId);
}