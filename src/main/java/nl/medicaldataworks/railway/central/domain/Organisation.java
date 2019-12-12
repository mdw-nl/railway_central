package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Organisation {
    @Id
    @GeneratedValue
    private Long id;
}
