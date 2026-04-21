package edge.verdant.service;

import edge.verdant.domain.entity.Report;
import edge.verdant.domain.entity.ReportData;
import edge.verdant.domain.vo.ReportDataVO;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

public interface ReportService {

    void generate(int type);

    Report getLast();

    ReportDataVO getData(Integer type, LocalDateTime begin, LocalDateTime end);

    void export(HttpServletResponse response, ReportData reportData);

    void exportA(HttpServletResponse response);

    void exportB(HttpServletResponse response);

    void exportC(HttpServletResponse response);

    ReportData getDataList(Integer type, LocalDateTime begin, LocalDateTime end);
}

