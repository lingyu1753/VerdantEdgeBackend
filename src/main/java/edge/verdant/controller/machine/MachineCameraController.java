package edge.verdant.controller.machine;

import edge.verdant.domain.dto.MachineCameraDTO;
import edge.verdant.domain.result.Result;
import edge.verdant.service.MachineCameraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@Tag(name = "设备摄影接口")
@RestController("machineMachineCameraController")
@RequestMapping("/machine/machineCamera")
public class MachineCameraController {
    @Autowired
    private MachineCameraService machineCameraService;

    /**
     * 定期摄影存储
     */
    @PostMapping
    @Operation(summary = "保存单次摄影数据")
    public Result<Void> save(HttpServletRequest request) {
        try {
            // 1. 提取请求头中的内容
            String result = request.getHeader("result");
            Integer status = Integer.valueOf(request.getHeader("status"));
            String id = request.getHeader("id");
            if (id == null || id.isEmpty()) {
                return Result.error("请求头中缺少 id");
            }
            Long machineId = Long.valueOf(id);

            // 2. 提取 Body 中的二进制流
            byte[] image = request.getInputStream().readAllBytes();
            if (image.length == 0) {
                return Result.error("请求体为空");
            }

            log.info("摄影设备：{}，摄影图像数据大小: {} bytes", id, image.length);

            MachineCameraDTO machineCameraDTO = MachineCameraDTO.builder()
                                                                .machineId(machineId)
                                                                .image(image)
                                                                .result(result)
                                                                .status(status)
                                                                .build();
            machineCameraService.merge(machineCameraDTO);
            return Result.success();
        } catch (Exception e) {
            log.error("处理上传数据失败", e);
            return Result.error("处理上传数据失败: " + e.getMessage());
        }
    }
}
