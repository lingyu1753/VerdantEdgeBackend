package edge.verdant.controller.admin;

import edge.verdant.domain.entity.MachineRecord;
import edge.verdant.domain.result.Result;
import edge.verdant.service.MachineRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "设备记录接口")
@RestController("adminMachineRecordController")
@RequestMapping("/admin/machineRecord")
public class MachineRecordController {
    @Autowired
    private MachineRecordService machineRecordService;

    @GetMapping
    @Operation(summary = "根据设备ID查询设备记录")
    public Result<MachineRecord> getByMachineId(@RequestParam Long id) {
        MachineRecord machineRecord = machineRecordService.getByMachineId(id);
        return Result.success(machineRecord);
    }
}
