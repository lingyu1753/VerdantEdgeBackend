package edge.verdant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edge.verdant.constant.MessageConstant;
import edge.verdant.exception.BaseException;
import edge.verdant.mapper.MachineMapper;
import edge.verdant.mapper.MachineRecordMapper;
import edge.verdant.domain.dto.MachineRecordDTO;
import edge.verdant.domain.entity.Machine;
import edge.verdant.domain.entity.MachineRecord;
import edge.verdant.service.MachineRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class MachineRecordServiceImpl extends ServiceImpl<MachineRecordMapper, MachineRecord> implements MachineRecordService {

    @Autowired
    private MachineRecordMapper machineRecordMapper;
    @Autowired
    private MachineMapper machineMapper;

    private static final Random RANDOM = new Random();

    /**
     * 保存或更新设备传感器最新数据（Upsert）
     * 若设备存在，则覆盖更新该设备的环境记录；若不存在记录则新增
     */
    @Override
    public void insert(MachineRecordDTO machineRecordDTO) {
        // 1. 校验设备是否存在
        Machine machine = machineMapper.selectById(machineRecordDTO.getMachineId());
        if (machine == null) {
            throw new BaseException(MessageConstant.MACHINE_NOT_FOUND);
        }

        // 2. 构建实体对象
        MachineRecord record = MachineRecord.builder().build();
        BeanUtils.copyProperties(machineRecordDTO, record);

        // 3. 对缺失的传感器字段使用随机值填充（模拟真实数据，生产环境应移除）
        fillMissingValuesWithRandom(record);

        // 4. 查询该设备是否已有记录
        MachineRecord existingRecord = machineRecordMapper.selectOne(
                new LambdaQueryWrapper<MachineRecord>()
                        .eq(MachineRecord::getMachineId, record.getMachineId())
                                                                    );

        LocalDateTime now = LocalDateTime.now();

        if (existingRecord != null) {
            existingRecord.setAtmosphericTemperature(record.getAtmosphericTemperature());
            existingRecord.setAtmosphericHumidity(record.getAtmosphericHumidity());
            existingRecord.setSoilHumidity(record.getSoilHumidity());
            existingRecord.setIlluminance(record.getIlluminance());
            existingRecord.setUpdateTime(now);
            machineRecordMapper.updateById(existingRecord);
            log.debug("更新设备环境记录: machineId={}", record.getMachineId());
        } else {
            // 插入新记录
            record.setUpdateTime(now);
            machineRecordMapper.insert(record);
            log.debug("新增设备环境记录: machineId={}", record.getMachineId());
        }
    }

    /**
     * 对空值字段填充随机模拟数据
     */
    private void fillMissingValuesWithRandom(MachineRecord record) {
        if (record.getAtmosphericTemperature() == null) {
            record.setAtmosphericTemperature(15.0 + RANDOM.nextDouble() * 25.0); // 15-40℃
        }
        if (record.getAtmosphericHumidity() == null) {
            record.setAtmosphericHumidity(30.0 + RANDOM.nextDouble() * 60.0); // 30-90%
        }
        if (record.getSoilHumidity() == null) {
            record.setSoilHumidity(20.0 + RANDOM.nextDouble() * 50.0); // 20-70%
        }
        if (record.getIlluminance() == null) {
            record.setIlluminance(1000.0 + RANDOM.nextDouble() * 9000.0); // 1000-10000 lux
        }
    }

    @Override
    public MachineRecord getByMachineId(Long machineId) {
        return machineRecordMapper.selectOne(
                new LambdaQueryWrapper<MachineRecord>().eq(MachineRecord::getMachineId, machineId)
                                            );
    }

    @Override
    public List<MachineRecord> getMachineRecordsByMachineIds(List<Long> machineIds) {
        return machineRecordMapper.selectList(
                new LambdaQueryWrapper<MachineRecord>().in(MachineRecord::getMachineId, machineIds)
                                             );
    }
}