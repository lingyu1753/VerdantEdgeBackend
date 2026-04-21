package edge.verdant.controller.admin;

import edge.verdant.domain.dto.MachineDTO;
import edge.verdant.domain.dto.MachineQueryDTO;
import edge.verdant.domain.dto.MachineUpdateDTO;
import edge.verdant.domain.entity.Machine;
import edge.verdant.domain.result.PageResult;
import edge.verdant.domain.result.Result;
import edge.verdant.domain.vo.MachineVO;
import edge.verdant.service.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("adminMachineController")
@RequestMapping("/admin/machine")
@Tag(name = "设备接口")
public class MachineController {
    @Autowired
    private MachineService machineService;

    @PostMapping("/page")
    @Operation(summary = "分页查询设备列表")
    public Result<PageResult<MachineVO>> page(@RequestBody MachineQueryDTO machineQueryDTO) {
        log.info("admin/machine/page: {}", machineQueryDTO);
        PageResult<MachineVO> pageResult = machineService.page(machineQueryDTO);
        return Result.success(pageResult);
    }

    @PutMapping("/updateOnline")
    @Operation(summary = "根据设备id查询设备是否在线")
    public Result<Integer> updateOnline(@RequestParam Long id){
        machineService.updateOnline(id);
        return Result.success();
    }

    @PostMapping
    @Operation(summary = "新增设备")
    public Result<Void> insert(@RequestBody MachineDTO machineDTO) {
        machineService.insert(machineDTO);
        return Result.success();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备")
    public Result<Void> delete(@RequestParam Long id) {
        machineService.delete(id);
        return Result.success();
    }

    @PutMapping
    @Operation(summary = "更新设备")
    public Result<Void> update(@RequestBody MachineUpdateDTO machineUpdateDTO) {
        machineService.update(machineUpdateDTO);
        return Result.success();
    }
}
