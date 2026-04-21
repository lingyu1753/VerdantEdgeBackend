package edge.verdant.service;

import edge.verdant.domain.dto.MachineRecordDTO;
import edge.verdant.domain.entity.MachineRecord;

import java.util.List;

public interface MachineRecordService {
    /**
     * 保存单次设备传感器数据
     */
    void insert(MachineRecordDTO machineRecordDTO);

    MachineRecord getByMachineId(Long machineId);

    List<MachineRecord> getMachineRecordsByMachineIds(List<Long> machineIds);
}

