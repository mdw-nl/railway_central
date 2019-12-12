package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class DataSource {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String organisationName;
}
