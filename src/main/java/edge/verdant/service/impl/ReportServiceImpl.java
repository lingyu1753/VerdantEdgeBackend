package edge.verdant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edge.verdant.domain.entity.*;
import edge.verdant.domain.vo.ReportDataVO;
import edge.verdant.mapper.ReportMapper;
import edge.verdant.service.*;
import edge.verdant.utils.CurrentHolder;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MachineService machineService;
    @Autowired
    private MachineRecordService machineRecordService;
    @Autowired
    private MachineCameraService machineCameraService;

    @Override
    public void generate(int type) {
        List<Long> empIds = employeeService.getEmployeeIds();
        for (Long empId : empIds) {
            List<Machine> machines = machineService.getMachinesByEmployeeId(empId);
            List<Long> machineIds = machines.stream().map(Machine::getId).collect(Collectors.toList());
            List<MachineRecord> machineRecords = machineRecordService.getMachineRecordsByMachineIds(machineIds);
            List<MachineCamera> machineCameras = machineCameraService.getMachineCamerasByMachineIds(machineIds);
            long onlineCount = machines.stream()
                                       .filter(machine -> machine.getOnline() == 1)
                                       .count();
            long totalCount = machines.size();
            long normalPlantCount = machineCameras.stream()
                                                  .filter(camera -> camera.getStatus() == 0)
                                                  .count();
            double averageAtmosphericTemperature = machineRecords.stream()
                                                                 .mapToDouble(MachineRecord::getAtmosphericTemperature)
                                                                 .average()
                                                                 .orElse(0.0);
            double averageAtmosphericHumidity = machineRecords.stream()
                                                              .mapToDouble(MachineRecord::getAtmosphericHumidity)
                                                              .average()
                                                              .orElse(0.0);
            double averageSoilHumidity = machineRecords.stream()
                                                       .mapToDouble(MachineRecord::getSoilHumidity)
                                                       .average()
                                                       .orElse(0.0);
            double averageIlluminance = machineRecords.stream()
                                                      .mapToDouble(MachineRecord::getIlluminance)
                                                      .average()
                                                      .orElse(0.0);
            Report report = Report.builder()
                                  .employeeId(empId)
                                  .time(LocalDateTime.now())
                                  .type(type)
                                  .onlineCount(onlineCount)
                                  .totalCount(totalCount)
                                  .normalPlantCount(normalPlantCount)
                                  .averageAtmosphericTemperature(averageAtmosphericTemperature)
                                  .averageAtmosphericHumidity(averageAtmosphericHumidity)
                                  .averageSoilHumidity(averageSoilHumidity)
                                  .averageIlluminance(averageIlluminance)
                                  .build();
            reportMapper.insert(report);
        }
    }

    @Override
    public Report getLast() {
        Employee employee = (Employee) CurrentHolder.getCurrent();
        return reportMapper.selectOne(new LambdaQueryWrapper<Report>()
                                              .eq(Report::getEmployeeId, employee.getId())
                                              .orderByDesc(true, Report::getTime)
                                              .last("limit 1")
                                     );
    }

    @Override
    public ReportData getDataList(Integer type, LocalDateTime begin, LocalDateTime end) {
        Employee employee = (Employee) CurrentHolder.getCurrent();
        List<LocalDateTime> dateList = new ArrayList<>();

        dateList.add(begin);

        if (type == 0) {
            // 按小时递增，直到begin小于end
            LocalDateTime current = begin;
            while (current.isBefore(end)) {
                current = current.plusHours(1);
                if (!current.isAfter(end)) {
                    dateList.add(current);
                }
            }
        } else if (type == 1) {
            // 按天递增，直到begin小于end
            LocalDateTime current = begin;
            while (current.isBefore(end)) {
                current = current.plusDays(1);
                if (!current.isAfter(end)) {
                    dateList.add(current);
                }
            }
        }

        List<String> dateDataList = new ArrayList<>();
        List<Double> averageAtmosphericTemperatureList = new ArrayList<>();
        List<Double> averageAtmosphericHumidityList = new ArrayList<>();
        List<Double> averageSoilHumidityList = new ArrayList<>();
        List<Double> averageIlluminanceList = new ArrayList<>();
        List<Double> healthyPlantPercentageList = new ArrayList<>();
        List<Double> diseasePlantPercentageList = new ArrayList<>();

        for (LocalDateTime date : dateList) {
            LocalDateTime beginTime;
            LocalDateTime endTime;
            String dateFormat;
            if (type == 0) {
                beginTime = date.withMinute(0).withSecond(0).withNano(0);
                endTime = date.withMinute(59).withSecond(59).withNano(999999999);
                dateFormat = beginTime.toLocalTime().toString();
            } else {
                beginTime = date.toLocalDate().atStartOfDay();
                endTime = date.toLocalDate().atTime(23, 59, 59, 999999999);
                dateFormat = beginTime.toLocalDate().toString();
            }

            dateDataList.add(dateFormat);
            LambdaQueryWrapper<Report> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Report::getEmployeeId, employee.getId())
                   .eq(Report::getType, type)
                   .between(Report::getTime, beginTime, endTime)
                   .orderByAsc(Report::getTime)
                   .last("LIMIT 1");
            Report report = reportMapper.selectOne(wrapper);

            // 如果找到报告，添加数据；否则添加默认值
            if (report != null) {
                averageAtmosphericTemperatureList.add(report.getAverageAtmosphericTemperature());
                averageAtmosphericHumidityList.add(report.getAverageAtmosphericHumidity());
                averageSoilHumidityList.add(report.getAverageSoilHumidity());
                averageIlluminanceList.add(report.getAverageIlluminance());

                // 计算健康植物百分比
                if (report.getTotalCount() > 0) {
                    double healthyPercentage = (double) report.getNormalPlantCount() / report.getTotalCount() * 100;
                    double diseasePercentage = 100.0 - healthyPercentage;
                    healthyPlantPercentageList.add(healthyPercentage);
                    diseasePlantPercentageList.add(diseasePercentage);
                } else {
                    healthyPlantPercentageList.add(100.0);
                    diseasePlantPercentageList.add(0.0);
                }
            } else {
                // 没有数据时添加默认值
                averageAtmosphericTemperatureList.add(0.0);
                averageAtmosphericHumidityList.add(0.0);
                averageSoilHumidityList.add(0.0);
                averageIlluminanceList.add(0.0);
                healthyPlantPercentageList.add(100.0);
                diseasePlantPercentageList.add(0.0);
            }
        }

        return ReportData.builder()
                         .dateList(dateDataList)
                         .averageAtmosphericTemperatureList(averageAtmosphericTemperatureList)
                         .averageAtmosphericHumidityList(averageAtmosphericHumidityList)
                         .averageSoilHumidityList(averageSoilHumidityList)
                         .averageIlluminanceList(averageIlluminanceList)
                         .healthyPlantPercentageList(healthyPlantPercentageList)
                         .diseasePlantPercentageList(diseasePlantPercentageList)
                         .build();
    }

    @Override
    public ReportDataVO getData(Integer type, LocalDateTime begin, LocalDateTime end) {
        ReportData reportData = getDataList(type, begin, end);
        ReportDataVO reportDataVO = ReportDataVO.builder()
                                                .dateData(StringUtils.join(reportData.getDateList(), ","))
                                                .averageAtmosphericTemperatureData("[" + StringUtils.join(reportData.getAverageAtmosphericTemperatureList(), ",") + "]")
                                                .averageAtmosphericHumidityData("[" + StringUtils.join(reportData.getAverageAtmosphericHumidityList(), ",") + "]")
                                                .averageSoilHumidityData("[" + StringUtils.join(reportData.getAverageSoilHumidityList(), ",") + "]")
                                                .averageIlluminanceData("[" + StringUtils.join(reportData.getAverageIlluminanceList(), ",") + "]")
                                                .healthyPlantPercentageData("[" + StringUtils.join(reportData.getHealthyPlantPercentageList(), ",") + "]")
                                                .diseasePlantPercentageData("[" + StringUtils.join(reportData.getDiseasePlantPercentageList(), ",") + "]")
                                                .build();
        return reportDataVO;
    }

    @Override
    public void export(HttpServletResponse response, ReportData reportData) {
        // 设置响应头（必须放在获取输出流之前）
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=数据统计报告_" +
                                                  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx");
        Report report = getLast();

        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("template/ReportExcelTemplate.xlsx");
             XSSFWorkbook excel = new XSSFWorkbook(in);
             ServletOutputStream out = response.getOutputStream()) {

            // ========== 1. 填充“最新报告”Sheet ==========
            XSSFSheet lastReportSheet = excel.getSheet("最新报告");
            setCellValue(lastReportSheet, 1, 0, report.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            setCellValue(lastReportSheet, 1, 1, report.getOnlineCount());
            setCellValue(lastReportSheet, 1, 2, report.getTotalCount());
            setCellValue(lastReportSheet, 1, 3, report.getAverageAtmosphericTemperature());
            setCellValue(lastReportSheet, 1, 4, report.getAverageAtmosphericHumidity());
            setCellValue(lastReportSheet, 1, 5, report.getAverageSoilHumidity());
            setCellValue(lastReportSheet, 1, 6, report.getAverageIlluminance());
            setCellValue(lastReportSheet, 1, 7, report.getNormalPlantCount());
            setCellValue(lastReportSheet, 1, 8, report.getTotalCount() - report.getNormalPlantCount());

            // ========== 2. 填充“趋势图”Sheet（每一列是一个时间点的完整数据） ==========
            XSSFSheet trendSheet = excel.getSheet("趋势图");
            int n = reportData.getDateList().size();
            for (int i = 0; i < n; i++) {
                int rowIndex = i + 1;
                setCellValue(trendSheet, rowIndex, 0, reportData.getDateList().get(i));                           // 日期
                setCellValue(trendSheet, rowIndex, 1, reportData.getAverageAtmosphericTemperatureList().get(i)); // 气温
                setCellValue(trendSheet, rowIndex, 2, reportData.getAverageAtmosphericHumidityList().get(i));    // 空气湿度
                setCellValue(trendSheet, rowIndex, 3, reportData.getAverageSoilHumidityList().get(i));           // 土壤湿度
                setCellValue(trendSheet, rowIndex, 4, reportData.getAverageIlluminanceList().get(i));            // 光照度
                setCellValue(trendSheet, rowIndex, 5, reportData.getHealthyPlantPercentageList().get(i));        // 健康占比
                setCellValue(trendSheet, rowIndex, 6, reportData.getDiseasePlantPercentageList().get(i));        // 病害占比
            }

            // 写入输出流
            excel.write(out);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("导出Excel文件失败", e);
        }
    }

    private void setCellValue(XSSFSheet sheet, int rowIndex, int colIndex, Object value) {
        XSSFRow row = sheet.getRow(rowIndex);
        if (row == null) row = sheet.createRow(rowIndex);
        XSSFCell cell = row.getCell(colIndex);
        if (cell == null) cell = row.createCell(colIndex);
        if (value == null) cell.setCellValue("");
        else if (value instanceof String) cell.setCellValue((String) value);
        else if (value instanceof Number) cell.setCellValue(((Number) value).doubleValue());
        else if (value instanceof Boolean) cell.setCellValue((Boolean) value);
        else if (value instanceof LocalDateTime) cell.setCellValue(((LocalDateTime) value).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        else cell.setCellValue(value.toString());
    }

    @Override
    public void exportA(HttpServletResponse response) {
        export(response, getDataList(0, LocalDateTime.now().plusDays(-1), LocalDateTime.now()));
    }

    @Override
    public void exportB(HttpServletResponse response) {
        export(response, getDataList(1, LocalDateTime.now().plusDays(-7), LocalDateTime.now()));
    }

    @Override
    public void exportC(HttpServletResponse response) {
        export(response, getDataList(1, LocalDateTime.now().plusDays(-30), LocalDateTime.now()));
    }
}
