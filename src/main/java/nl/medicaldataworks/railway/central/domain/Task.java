package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
}
