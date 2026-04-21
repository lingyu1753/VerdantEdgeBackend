package edge.verdant.service;

import edge.verdant.domain.dto.EmployeeLoginDTO;
import edge.verdant.domain.dto.EmployeeRegisterDTO;
import edge.verdant.domain.entity.Employee;
import edge.verdant.domain.vo.EmployeeLoginVO;

import java.util.*;

public interface EmployeeService {
    /**
     * 登录接口
     */
    EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 添加员工
     */
    void register(EmployeeRegisterDTO employeeRegisterDTO);

    /**
     * 根据id查询员工
     */
    Employee getById(Long id);

    void update(Employee employee);

    List<Long> getEmployeeIds();

    void initAdminAccount();
}
