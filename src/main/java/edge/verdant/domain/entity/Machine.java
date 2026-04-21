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
@TableName("machine")
public class Machine {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO) // 指定主键生成策略
    private Long id;

    @TableField(value = "employee_id")
    private Long employeeId;

    /**
     * 设备名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 设备编号
     */
    @TableField(value = "number")
    private int number;

    /**
     * 植物名称
     */
    private String plantName;

    /**
     * 植物类型
     */
    @TableField(value = "plant_type")
    private String plantType;

    /**
     * 设备是否在线
     */
    @TableField(value = "online")
    private Integer online;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
