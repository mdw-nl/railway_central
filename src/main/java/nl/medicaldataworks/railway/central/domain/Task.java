package nl.medicaldataworks.railway.central.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
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
    private String input;
    private boolean master;
    @Column(nullable = false)
    private Long iteration;
    private String error;
    private String logLocation;
}