package edge.verdant.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MachineQueryDTO {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    private Integer number;
    private String plantName;
    private String plantType;
    private Integer online;
}

