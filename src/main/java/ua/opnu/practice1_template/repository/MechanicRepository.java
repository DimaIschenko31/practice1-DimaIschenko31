package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.Mechanic;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Long> {
}