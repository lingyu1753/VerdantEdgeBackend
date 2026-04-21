package edge.verdant.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MachineRecordDTO {
    private Long machineId;
    private Integer number;// 设备编号
    private Double atmosphericTemperature;// 空气温度
    private Double atmosphericHumidity;// 空气湿度
    private Double soilHumidity;// 土壤湿度
    private Double illuminance;// 光照强度
    private LocalDateTime createTime;// 创建时间
}
