package nl.medicaldataworks.railway.central.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date creationTimestamp;
    private Long trainId;
    @Column(nullable = false)
    private CalculationStatus calculationStatus;
    private String result;
    private Long stationId;
    //TODO Task should have a field for input values for the algorithm
}