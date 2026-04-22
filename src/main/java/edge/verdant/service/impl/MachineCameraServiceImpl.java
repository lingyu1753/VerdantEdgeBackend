package edge.verdant.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import edge.verdant.constant.PlantConstant;
import edge.verdant.domain.dto.MachineCameraDTO;
import edge.verdant.domain.entity.MachineCamera;
import edge.verdant.domain.entity.MachineRecord;
import edge.verdant.mapper.EmployeeMapper;
import edge.verdant.mapper.MachineCameraMapper;
import edge.verdant.mapper.MachineMapper;
import edge.verdant.domain.entity.Employee;
import edge.verdant.domain.entity.Machine;
import edge.verdant.mapper.MachineRecordMapper;
import edge.verdant.service.AIService;
import edge.verdant.service.MachineCameraService;
import edge.verdant.utils.AliyunOSSOperator;
import edge.verdant.utils.EmailSender;
import edge.verdant.utils.EmailUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MachineCameraServiceImpl implements MachineCameraService {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @Autowired
    private MachineCameraMapper machineCameraMapper;
    @Autowired
    private MachineRecordMapper machineRecordMapper;
    @Autowired
    private MachineMapper machineMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmailSender emailSender;
    @Autowired
    private AIService aiService;

    /**
     * 保存单次摄影图片
     */

    public void merge(MachineCameraDTO machineCameraDTO) {
        byte[] image = machineCameraDTO.getImage();
        String url = aliyunOSSOperator.upload(image, "machine-camera/" + machineCameraDTO.getMachineId() + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + ".jpg");
        MachineCamera machineCamera = machineCameraMapper.selectOne(
                new LambdaQueryWrapper<MachineCamera>().eq(MachineCamera::getMachineId, machineCameraDTO.getMachineId())
                                                                   );
        log.info("相机原始数据：{}", machineCamera);
        if (machineCamera == null) {
            machineCamera = MachineCamera.builder()
                                         .machineId(machineCameraDTO.getMachineId())
                                         .build();
        }

        machineCamera.setResult(machineCameraDTO.getResult());
        machineCamera.setStatus(machineCameraDTO.getStatus());
        machineCamera.setImageUrl(url);
        log.info("相机完成后数据：{}", machineCamera);
        if (machineCamera.getId() == null) machineCameraMapper.insert(machineCamera);
        else machineCameraMapper.updateById(machineCamera);

        machineMapper.update(new LambdaUpdateWrapper<Machine>()
                                     .eq(Machine::getId, machineCamera.getMachineId())
                                     .set(Machine::getUpdateTime, LocalDateTime.now()));

        int status = machineCamera.getStatus();
        if (status == PlantConstant.DISEASE) {
            Machine machine = machineMapper.selectById(machineCamera.getMachineId());
            MachineRecord machineRecord = machineRecordMapper.selectOne(new LambdaQueryWrapper<MachineRecord>().eq(MachineRecord::getMachineId, machineCamera.getMachineId()));
            Employee employee = employeeMapper.selectById(machine.getEmployeeId());
            emailSender.send(employee.getEmail(), "植物病变报警通知", EmailUtils.buildWarningMessage(
                    employee.getRealName(),
                    machine.getNumber(),
                    aiService.generateWarningMessage(machine, machineRecord, machineCamera)
                                                                                                    ));
        }

    }

    @Override
    public List<MachineCamera> getMachineCamerasByMachineIds(List<Long> machineIds) {
        return machineCameraMapper.selectList(new LambdaQueryWrapper<MachineCamera>().in(MachineCamera::getMachineId, machineIds));
    }

    @Override
    public MachineCamera getByMachineId(Long machineId) {
        return machineCameraMapper.selectOne(new LambdaQueryWrapper<MachineCamera>().eq(MachineCamera::getMachineId, machineId));
    }
}
