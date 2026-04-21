package edge.verdant.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("machine_camera")
public class MachineCameraDTO {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 设备id
     */
    @TableField(value = "machine_id")
    private Long machineId;

    /**
     * 图片
     */
    private byte[] image;

    /**
     * 诊断结果
     */
    // 0为正常，1为病变
    @TableField(value = "result")
    private String result;

    /**
     * 警告内容
     */
    @TableField(value = "status")
    private Integer status;
}
