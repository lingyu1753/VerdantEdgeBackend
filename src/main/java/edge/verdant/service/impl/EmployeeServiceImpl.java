package edge.verdant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edge.verdant.constant.MessageConstant;
import edge.verdant.exception.BaseException;
import edge.verdant.mapper.EmployeeMapper;
import edge.verdant.domain.dto.EmployeeLoginDTO;
import edge.verdant.domain.dto.EmployeeRegisterDTO;
import edge.verdant.domain.entity.Employee;
import edge.verdant.domain.vo.EmployeeLoginVO;
import edge.verdant.service.EmployeeService;
import edge.verdant.utils.CurrentHolder;
import edge.verdant.utils.EmailUtils;
import edge.verdant.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     */
    @Override
    public EmployeeLoginVO login(EmployeeLoginDTO employeeLoginDTO) {
        Employee employee = employeeMapper.selectOne(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getName, employeeLoginDTO.getName())
                                                    );
        if (employee == null) {
            throw new BaseException(MessageConstant.EMPLOYEE_NAME_NOT_FOUND);
        }

        // 使用 BCrypt 的 matches 方法验证
        if (!employeeLoginDTO.getPassword().equals(employee.getPassword())) {
            throw new BaseException(MessageConstant.PASSWORD_ERROR);
        }

        // 生成 token 等后续逻辑...
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", employee.getId());
        String jwt = JwtUtils.generateToken(map);
        EmployeeLoginVO vo = EmployeeLoginVO.builder().token(jwt).build();
        BeanUtils.copyProperties(employee, vo);
        vo.setPassword("******");
        return vo;
    }

    /**
     * 添加员工
     */
    @Override
    public void register(EmployeeRegisterDTO employeeRegisterDTO) {
        Employee existingEmployee = employeeMapper.selectOne(
                new LambdaQueryWrapper<Employee>()
                        .eq(Employee::getName, employeeRegisterDTO.getName())
                                                            );
        if (existingEmployee != null) {
            throw new BaseException(MessageConstant.EMPLOYEE_NAME_ALREADY_EXISTS);
        }

        if (!EmailUtils.isValid(employeeRegisterDTO.getEmail())) {
            throw new BaseException(MessageConstant.EMAIL_FORMAT_ERROR);
        }

        Employee employee = Employee.builder().build();
        BeanUtils.copyProperties(employeeRegisterDTO, employee);
        employee.setPassword(employeeRegisterDTO.getPassword());
        employeeMapper.insert(employee);
    }

    /**
     * 根据id查询员工
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.selectById(id);
        if (employee == null) return null;
        employee.setPassword("******");
        return employee;
    }

    @Override
    public void update(Employee employee) {
        Employee operator = (Employee) CurrentHolder.getCurrent();
        employee.setId(operator.getId());
        if (!EmailUtils.isValid(employee.getEmail())) {
            throw new BaseException(MessageConstant.EMAIL_FORMAT_ERROR);
        }
        employee.setPassword(employee.getPassword());
        employeeMapper.updateById(employee);
    }

    @Override
    public List<Long> getEmployeeIds() {
        List<Employee> employees = employeeMapper.selectList(null);
        return employees.stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList());
    }

    @Override
    public void initAdminAccount() {
        Employee existingAdmin = employeeMapper.selectOne(
                new LambdaQueryWrapper<Employee>().eq(Employee::getName, "admin")
                                                         );

        if (existingAdmin == null) {
            // 创建管理员账户
            Employee admin = Employee.builder()
                                     .id(1L)
                                     .name("admin")
                                     .password("123456")
                                     .email("2975194966@qq.com")
                                     .phone("18025447710")
                                     .realName("予梦玲羽")
                                     .number("44058202120204152")
                                     .build();

            employeeMapper.insert(admin);
            log.info("管理员账户初始化成功: admin");
        } else {
            log.info("管理员账户已存在: admin");
        }
    }
}