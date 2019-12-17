package nl.medicaldataworks.railway.central.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    @CreationTimestamp
    private Date creationTimestamp;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JsonIdentityReference(alwaysAsId = true)
    private Train train;
    @Column(nullable = false)
    private CalculationStatus calculationStatus;
    private String result;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private Station station;
    private String ownerName;
}