package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Train train;
    private CalculationStatus calculationStatus;
    private String result;
    private String clientId;
    private String ownerName;
}