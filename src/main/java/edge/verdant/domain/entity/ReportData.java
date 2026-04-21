package edge.verdant.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ReportData {
    List<String> dateList;
    List<Double> averageAtmosphericTemperatureList;
    List<Double> averageAtmosphericHumidityList;
    List<Double> averageSoilHumidityList;
    List<Double> averageIlluminanceList;
    List<Double> healthyPlantPercentageList;
    List<Double> diseasePlantPercentageList;
}
