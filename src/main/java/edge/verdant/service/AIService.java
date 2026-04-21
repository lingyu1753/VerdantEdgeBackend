package edge.verdant.service;

import edge.verdant.domain.entity.Machine;
import edge.verdant.domain.entity.MachineCamera;
import edge.verdant.domain.entity.MachineRecord;
import edge.verdant.domain.entity.Report;
import edge.verdant.domain.vo.ReportDataVO;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface AIService {
    String syncOutput(String modelName, String systemPreset, String userContent);

    Flux<String> fluxOutput(String modelName, String systemPreset, String userContent);

    String generateWarningMessage(Machine machine, MachineRecord machineRecord, MachineCamera machineCamera);

    Flux<String> summarizeDevice(Machine machine, MachineRecord machineRecord, MachineCamera machineCamera);

    Flux<String> summarizeReport(Report report, ReportDataVO reportDataVO);
}
