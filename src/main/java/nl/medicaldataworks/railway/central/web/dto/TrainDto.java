package nl.medicaldataworks.railway.central.web.dto;

import lombok.Data;
import nl.medicaldataworks.railway.central.domain.CalculationStatus;

@Data
public class TrainDto {
    private Long id;
    private String name;
    private String dockerImageUrl;
    private CalculationStatus calculationStatus;
}
