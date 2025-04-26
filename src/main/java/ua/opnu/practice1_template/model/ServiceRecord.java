package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference("car-service")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "mechanic_id")
    @JsonBackReference("mechanic-service")
    private Mechanic mechanic;

    private LocalDate date;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "service_record_types",
            joinColumns = @JoinColumn(name = "service_record_id"),
            inverseJoinColumns = @JoinColumn(name = "service_type_id")
    )
    @JsonManagedReference("service-types")
    private List<ServiceType> serviceTypes;
}