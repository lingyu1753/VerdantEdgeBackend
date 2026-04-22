package edge.verdant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edge.verdant.domain.dto.MachineDTO;
import edge.verdant.domain.dto.MachineQueryDTO;
import edge.verdant.domain.dto.MachineUpdateDTO;
import edge.verdant.domain.entity.Employee;
import edge.verdant.domain.entity.MachineCamera;
import edge.verdant.domain.entity.MachineRecord;
import edge.verdant.domain.result.PageResult;
import edge.verdant.domain.vo.MachineVO;
import edge.verdant.mapper.MachineCameraMapper;
import edge.verdant.mapper.MachineMapper;
import edge.verdant.domain.entity.Machine;
import edge.verdant.mapper.MachineRecordMapper;
import edge.verdant.service.EmployeeService;
import edge.verdant.service.MachineService;
import edge.verdant.utils.CurrentHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MachineServiceImpl extends ServiceImpl<MachineMapper, Machine> implements MachineService {
    @Autowired
    private MachineMapper machineMapper;
    @Autowired
    private MachineRecordMapper machineRecordMapper;
    @Autowired
    private MachineCameraMapper machineCameraMapper;

    @Override
    public PageResult<MachineVO> page(MachineQueryDTO machineQueryDTO) {
        Employee emp = (Employee) CurrentHolder.getCurrent();
        Page<Machine> page = Page.of(machineQueryDTO.getPage(), machineQueryDTO.getPageSize());
        page.orders().add(new OrderItem().setColumn("id").setAsc(false));
        Page<Machine> p = this.page(page, new LambdaQueryWrapper<Machine>().eq(Machine::getEmployeeId, emp.getId())
                                                                           .eq(machineQueryDTO.getOnline() != null, Machine::getOnline, machineQueryDTO.getOnline())
                                                                           .like(StringUtils.hasText(machineQueryDTO.getName()), Machine::getName, machineQueryDTO.getName())
                                                                           .eq(machineQueryDTO.getNumber() != null, Machine::getNumber, machineQueryDTO.getNumber())
                                                                           .like(StringUtils.hasText(machineQueryDTO.getPlantName()), Machine::getPlantName, machineQueryDTO.getPlantName())
                                                                           .like(StringUtils.hasText(machineQueryDTO.getPlantType()), Machine::getPlantType, machineQueryDTO.getPlantType())
                                   );
        List<Machine> machineList = p.getRecords();
        List<MachineVO> voList = new ArrayList<>();
        for (Machine machine : machineList) {
            MachineCamera machineCamera = machineCameraMapper.selectOne(new LambdaQueryWrapper<MachineCamera>().eq(MachineCamera::getMachineId, machine.getId()));
            if (machineCamera == null) {
                machineCamera = MachineCamera.builder()
                                             .machineId(machine.getId())
                                             .imageUrl("")
                                             .status(0)
                                             .result("无")
                                             .build();
            }
            MachineVO vo = MachineVO.builder()
                                    .imageUrl(machineCamera.getImageUrl())
                                    .status(machineCamera.getStatus())
                                    .result(machineCamera.getResult())
                                    .build();
            BeanUtils.copyProperties(machine, vo);
            voList.add(vo);
        }
        return new PageResult<>(p.getTotal(), voList);
    }

    @Override
    public void insert(MachineDTO machineDTO) {
        Employee emp = (Employee) CurrentHolder.getCurrent();
        Machine machine = Machine.builder()
                                 .online(0)
                                 .createTime(LocalDateTime.now())
                                 .updateTime(LocalDateTime.now())
                                 .employeeId(emp.getId())
                                 .build();
        BeanUtils.copyProperties(machineDTO, machine);
        this.save(machine);

        MachineRecord machineRecord = MachineRecord.builder()
                                                   .machineId(machine.getId())
                                                   .updateTime(LocalDateTime.now())
                                                   .build();
        machineRecordMapper.insert(machineRecord);

        MachineCamera machineCamera = MachineCamera.builder()
                                                .machineId(machine.getId())
                                                .imageUrl("https://lingyu175-web.oss-cn-shenzhen.aliyuncs.com/empty.png")
                                                .status(0)
                                                .result("你的植株当前一切健康")
                                                .build();
        machineCameraMapper.insert(machineCamera);
    }

    @Override
    public void updateOnline(Long id) {
        Machine machine = machineMapper.selectById(id);
        machine.setOnline(machine.getOnline() == 1 ? 0 : 1);
        machine.setUpdateTime(LocalDateTime.now());
        machineMapper.updateById(machine);
    }

    @Override
    public void delete(Long id) {
        machineMapper.deleteById(id);
        machineRecordMapper.delete(new LambdaQueryWrapper<MachineRecord>().eq(MachineRecord::getMachineId, id));
        machineCameraMapper.delete(new LambdaQueryWrapper<MachineCamera>().eq(MachineCamera::getMachineId, id));
    }

    @Override
    public void update(MachineUpdateDTO machineUpdateDTO) {
        Machine machine = machineMapper.selectById(machineUpdateDTO.getId());
        BeanUtils.copyProperties(machineUpdateDTO, machine);
        machine.setUpdateTime(LocalDateTime.now());
        machineMapper.updateById(machine);
    }

    @Override
    public List<Machine> getMachinesByEmployeeId(Long empId) {
        return machineMapper.selectList(new LambdaQueryWrapper<Machine>().eq(Machine::getEmployeeId, empId));
    }

    @Override
    public Machine getMachineById(Long id) {
        return machineMapper.selectById(id);
    }
}
