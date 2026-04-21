package edge.verdant.controller.admin;

import edge.verdant.domain.entity.*;
import edge.verdant.domain.vo.ReportDataVO;
import edge.verdant.service.*;
import edge.verdant.utils.CurrentHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Slf4j
@Tag(name = "AI接口")
@RestController("adminAIController")
@RequestMapping("/admin/ai")
public class AIController {
    @Autowired
    private AIService aiService;
    @Autowired
    private MachineService machineService;
    @Autowired
    private MachineRecordService machineRecordService;
    @Autowired
    private MachineCameraService machineCameraService;
    @Autowired
    private ReportService reportService;

    @GetMapping("/summarizeDevice")
    @Operation(summary = "总结单个设备")
    public Flux<String> summarizeDevice(@RequestParam Long id) {
        log.info("总结设备: {}",id);
        Machine machine = machineService.getMachineById(id);
        MachineRecord machineRecord = machineRecordService.getByMachineId(id);
        MachineCamera machineCamera = machineCameraService.getByMachineId(id);
        return aiService.summarizeDevice(machine, machineRecord, machineCamera);
    }

    @GetMapping("/summarizeReport")
    @Operation(summary = "总结报告")
    public Flux<String> summarizeReport(
            @RequestParam Integer type,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end) {
        Report report = reportService.getLast();
        ReportDataVO reportDataVO = reportService.getData(type, begin, end);
        return aiService.summarizeReport(report, reportDataVO);
    }
}
