package nl.medicaldataworks.railway.central.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
}