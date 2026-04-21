package edge.verdant.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MachineUpdateDTO {
    private Long id;

    private String name;

    private Integer number;

    private String plantName;

    private String plantType;
}
