package nl.medicaldataworks.railway.central.domain;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @CreationTimestamp
    private Date creationTimestamp;
    @ManyToOne() //cascade = CascadeType.PERSIST
    private Train train;
    private CalculationStatus calculationStatus;
    private String result;
    private String clientId;
    private String ownerName;
}