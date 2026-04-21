package edge.verdant.controller.admin;

import edge.verdant.domain.entity.Report;
import edge.verdant.domain.result.Result;
import edge.verdant.domain.vo.ReportDataVO;
import edge.verdant.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@Tag(name = "报告接口")
@RestController("adminReportController")
@RequestMapping("/admin/report")
public class ReportController {
    @Autowired
    private ReportService reportService;


    @GetMapping("/getLast")
    @Operation(summary = "获取当前报告")
    public Result<Report> getLast() {
        Report report = reportService.getLast();
        return Result.success(report);
    }

    @GetMapping("/getData")
    @Operation(summary = "报告数据获取")
    public Result<ReportDataVO> getData(
            @RequestParam Integer type,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end){
        log.info("报告数据统计：{},{}",begin,end);
        ReportDataVO reportDataVO = reportService.getData(type, begin,end);
        return Result.success(reportDataVO);
    }

    @GetMapping("/exportA")
    @Operation(summary = "报告导出")
    public Result<Void> exportA(HttpServletResponse response) {
        reportService.exportA(response);
        return Result.success();
    }

    @GetMapping("/exportB")
    @Operation(summary = "报告导出")
    public Result<Void> exportB(HttpServletResponse response) {
        reportService.exportB(response);
        return Result.success();
    }

    @GetMapping("/exportC")
    @Operation(summary = "报告导出")
    public Result<Void> exportC(HttpServletResponse response) {
        reportService.exportC(response);
        return Result.success();
    }
}
