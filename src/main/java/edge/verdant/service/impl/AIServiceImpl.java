package edge.verdant.service.impl;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.Role;
import edge.verdant.constant.SystemPresetInformation;
import edge.verdant.domain.entity.*;
import edge.verdant.domain.vo.ReportDataVO;
import edge.verdant.service.*;
import io.reactivex.Flowable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {
    @Value("${verdant.dashscope.api-key}")
    private String DASHSCOPE_API_KEY;

    @Override
    public String syncOutput(String modelName, String systemPreset, String userContent) {
        Message systemMsg = Message.builder()
                                   .role(Role.SYSTEM.getValue())
                                   .content(systemPreset)
                                   .build();
        Message userMsg = Message.builder()
                                 .role(Role.USER.getValue())
                                 .content(userContent)
                                 .build();

        // 关键改动：incrementalOutput(false) 关闭流式，gen.call() 同步阻塞获取完整结果
        GenerationParam param = GenerationParam.builder()
                                               .apiKey(DASHSCOPE_API_KEY)
                                               .model(modelName)                         // 建议使用 "qwen-plus-2025-07-28" 获得更快响应
                                               .messages(Arrays.asList(systemMsg, userMsg))
                                               .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                                               .enableSearch(true)
                                               .incrementalOutput(false)                 // 关闭增量输出，请求完整结果
                                               .build();

        Generation gen = new Generation();
        try {
            GenerationResult result = gen.call(param);    // 同步阻塞调用，直接拿到完整响应
            return result.getOutput().getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("AI同步调用失败: model={}, error={}", modelName, e.getMessage(), e);
            throw new RuntimeException("AI服务调用失败: " + e.getMessage(), e);
        }
    }

    @Override
    public Flux<String> fluxOutput(String modelName, String systemPreset, String userContent) {
        Message systemMsg = Message.builder()
                                   .role(Role.SYSTEM.getValue())
                                   .content(systemPreset)
                                   .build();
        Message userMsg = Message.builder()
                                 .role(Role.USER.getValue())
                                 .content(userContent)
                                 .build();
        GenerationParam param = GenerationParam.builder()
                                               .apiKey(DASHSCOPE_API_KEY)
                                               .model(modelName)
                                               .messages(Arrays.asList(systemMsg, userMsg))
                                               .resultFormat(GenerationParam.ResultFormat.MESSAGE)
                                               .enableSearch(true)
                                               .incrementalOutput(true)
                                               .build();
        Generation gen = new Generation();
        return Flux.create(emitter -> {
            try {
                Flowable<GenerationResult> result = gen.streamCall(param);
                result.subscribe(
                        message -> {// onNext: 处理每个响应片段
                            String content = message.getOutput().getChoices().get(0).getMessage().getContent();
                            String finishReason = message.getOutput().getChoices().get(0).getFinishReason();
                            emitter.next(content);// 将内容发送到 Flux
                            if (finishReason != null && !"null".equals(finishReason)) {// 当 finishReason 不为 null 时，表示是最后一个 chunk
                                emitter.complete();// 结束输出
                            }
                        },
                        error -> {// onError: 处理错误
                            log.error("请求失败: {}", error.getMessage(), error);
                            emitter.error(new RuntimeException("AI服务调用失败: " + error.getMessage()));
                        },
                        () -> {// onComplete: 完成回调（如果没有通过finishReason触发complete的话）
                            if (!emitter.isCancelled()) emitter.complete();
                        }
                                );

            } catch (Exception e) {
                log.error("请求异常: {}", e.getMessage(), e);
                emitter.error(new RuntimeException("AI服务调用异常: " + e.getMessage()));
            }
        }, FluxSink.OverflowStrategy.BUFFER);
    }

    @Override
    public Flux<String> summarizeDevice(Machine machine, MachineRecord machineRecord, MachineCamera machineCamera) {
        return fluxOutput("qwen-plus", SystemPresetInformation.SUMMARY_DEVICE, machine + "\n" + machineRecord + "\n" + machineCamera);
    }

    @Override
    public Flux<String> summarizeReport(Report report, ReportDataVO reportDataVO) {
        return fluxOutput("qwen-plus", SystemPresetInformation.SUMMARY_REPORT, report + "\n" + reportDataVO);
    }

    @Override
    public String generateWarningMessage(Machine machine, MachineRecord machineRecord, MachineCamera machineCamera) {
        return syncOutput("qwen-plus", SystemPresetInformation.WARNING_MESSAGE, machine + "\n" + machineRecord + "\n" + machineCamera);
    }
}
