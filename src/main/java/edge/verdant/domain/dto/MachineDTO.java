package edge.verdant.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MachineDTO {

    private String name;

    private Integer number;

    private String plantName;

    private String plantType;
}
