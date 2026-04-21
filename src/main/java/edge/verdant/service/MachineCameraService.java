package edge.verdant.service;

import edge.verdant.domain.dto.MachineCameraDTO;
import edge.verdant.domain.entity.MachineCamera;

import java.util.List;

public interface MachineCameraService {
    /**
     * 保存单次设备摄影数据
     */
    void merge(MachineCameraDTO machineCameraDTO);

    List<MachineCamera> getMachineCamerasByMachineIds(List<Long> machineIds);

    MachineCamera getByMachineId(Long machineId);
}
