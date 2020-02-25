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
    @Column(columnDefinition = "TEXT")
    private String result;
    private Long stationId;
    @Column(columnDefinition = "TEXT")
    private String input;
    private boolean master;
    @Column(nullable = false)
    private Long iteration;
    @Column(columnDefinition = "VARCHAR(20000)")
    private String error;
    @Column(columnDefinition = "VARCHAR(2000)")
    private String logLocation;
}
