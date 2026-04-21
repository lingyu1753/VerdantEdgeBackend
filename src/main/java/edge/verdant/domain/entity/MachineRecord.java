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
@TableName("machine_record")
public class MachineRecord {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO) // 指定主键生成策略
    private Long id;

    @TableField(value = "machine_id")
    private Long machineId;

    @TableField(value = "atmospheric_temperature")
    private Double atmosphericTemperature;

    @TableField(value = "atmospheric_humidity")
    private Double atmosphericHumidity;

    @TableField(value = "soil_humidity")
    private Double soilHumidity;

    @TableField(value = "illuminance")
    private Double illuminance;

    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
