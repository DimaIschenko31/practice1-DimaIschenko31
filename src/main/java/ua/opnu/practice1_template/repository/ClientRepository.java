package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}