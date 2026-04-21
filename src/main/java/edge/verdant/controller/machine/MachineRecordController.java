package edge.verdant.controller.machine;

import edge.verdant.domain.dto.MachineRecordDTO;
import edge.verdant.domain.result.Result;
import edge.verdant.service.MachineRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "设备传感器记录接口")
@RestController("machineMachineRecordController")
@RequestMapping("/machine/machineRecord")
public class MachineRecordController {
    @Autowired
    private MachineRecordService machineRecordService;

    @PostMapping
    @Operation(summary = "保存单词传感器记录")
    public Result<Void> save(@RequestBody MachineRecordDTO machineRecordDTO){
        log.info("记录设备传感器数据：{}",machineRecordDTO);
        machineRecordService.insert(machineRecordDTO);
        return Result.success();
    }
}
