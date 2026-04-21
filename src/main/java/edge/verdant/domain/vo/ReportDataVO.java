package edge.verdant.domain.vo;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@ToString
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReportDataVO {
    String dateData;
    String averageAtmosphericTemperatureData;
    String averageAtmosphericHumidityData;
    String averageSoilHumidityData;
    String averageIlluminanceData;
    String healthyPlantPercentageData;
    String diseasePlantPercentageData;
}
