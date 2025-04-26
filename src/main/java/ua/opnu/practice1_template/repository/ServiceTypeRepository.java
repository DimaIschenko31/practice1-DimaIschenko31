package ua.opnu.practice1_template.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.opnu.practice1_template.model.ServiceType;

import java.util.List;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    @Query("SELECT st FROM ServiceType st JOIN st.serviceRecords sr GROUP BY st ORDER BY COUNT(sr) DESC")
    List<ServiceType> findMostPopularServiceTypes();
}