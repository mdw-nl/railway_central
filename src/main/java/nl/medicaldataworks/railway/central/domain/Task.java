package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Train train;
    private CalculationStatus calculationStatus;
    private String result;
}