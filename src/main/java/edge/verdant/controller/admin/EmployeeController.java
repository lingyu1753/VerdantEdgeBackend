package edge.verdant.controller.admin;

import edge.verdant.domain.dto.EmployeeLoginDTO;
import edge.verdant.domain.dto.EmployeeRegisterDTO;
import edge.verdant.domain.entity.Employee;
import edge.verdant.domain.vo.EmployeeLoginVO;
import edge.verdant.domain.result.Result;
import edge.verdant.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工接口")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    @Operation(summary = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录: {}", employeeLoginDTO);
        EmployeeLoginVO vo = employeeService.login(employeeLoginDTO);
        return Result.success(vo);
    }


    @PostMapping
    @Operation(summary = "新增员工")
    public Result<Void> register(@RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        log.info("添加员工: {}", employeeRegisterDTO);
        employeeService.register(employeeRegisterDTO);
        return Result.success();
    }

    /**
     * 根据id查询员工信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询员工")
    public Result<Employee> getById(@PathVariable("id") Long id){
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

    @PutMapping
    @Operation(summary = "更新员工")
    public Result<Void> update(@RequestBody Employee employee){
        employeeService.update(employee);
        return Result.success();
    }
}
