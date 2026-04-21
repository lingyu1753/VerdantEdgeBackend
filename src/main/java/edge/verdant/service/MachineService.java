package edge.verdant.service;

import edge.verdant.domain.dto.MachineDTO;
import edge.verdant.domain.dto.MachineQueryDTO;
import edge.verdant.domain.dto.MachineUpdateDTO;
import edge.verdant.domain.entity.Machine;
import edge.verdant.domain.result.PageResult;
import edge.verdant.domain.vo.MachineVO;

import java.util.List;

public interface MachineService {

    PageResult<MachineVO> page(MachineQueryDTO param);

    void insert(MachineDTO machine);

    void updateOnline(Long id);

    void delete(Long id);

    void update(MachineUpdateDTO machineUpdateDTO);

    List<Machine> getMachinesByEmployeeId(Long empId);

    Machine getMachineById(Long id);
}
