package ua.opnu.practice1_template.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @JsonBackReference("client-car")
    private Client client;

    private String make;
    private String model;
    private Integer year;
    private String vin;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    @JsonManagedReference("car-service")
    private List<ServiceRecord> serviceRecords;
}