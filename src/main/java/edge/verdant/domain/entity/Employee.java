package edge.verdant.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "employee", autoResultMap = true)
public class Employee {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 员工名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 账户密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 员工邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 员工手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    private String realName;

    /**
     * 身份证号
     */
    @TableField(value = "number")
    private String number;
}
