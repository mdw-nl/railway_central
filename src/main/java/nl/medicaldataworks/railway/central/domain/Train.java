package nl.medicaldataworks.railway.central.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @OneToMany(mappedBy = "train",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Task> tasks;
}
