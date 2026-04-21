package edge.verdant.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MachineVO {
    private Long id;
    private Long employeeId;
    private String name;
    private int number;
    private String plantName;
    private String plantType;
    private Integer online;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String imageUrl;
    private String result;
    private Integer status;//0正常 1病变
}

