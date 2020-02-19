package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "train")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false)
    private String dockerImageUrl;
    @Column(nullable = false)
    private String ownerId;
    @Column(nullable = false)
    private CalculationStatus calculationStatus;
    @Column(nullable = false)
    private Long currentIteration;
    private Long clientTaskCount;
}
