package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Train {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String dockerImageUrl;
    private String ownerName;
    private CalculationStatus calculationStatus;
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL)
    private List<Task> tasks;
}
