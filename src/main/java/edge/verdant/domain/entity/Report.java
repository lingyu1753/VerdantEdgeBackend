package edge.verdant.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "report", autoResultMap = true)
public class Report {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "employee_id")
    private Long employeeId;

    @TableField(value = "time")
    private LocalDateTime time;

    @TableField(value = "type")
    private Integer type;//0小时, 1天

    @TableField(value = "online_count")
    private Long onlineCount;

    @TableField(value = "total_count")
    private Long totalCount;

    @TableField(value = "normal_plant_count")
    private Long normalPlantCount;

    @TableField(value = "average_atmospheric_temperature")
    private Double averageAtmosphericTemperature;

    @TableField(value = "average_atmospheric_humidity")
    private Double averageAtmosphericHumidity;

    @TableField(value = "average_soil_humidity")
    private Double averageSoilHumidity;

    @TableField(value = "average_illuminance")
    private Double averageIlluminance;
}
