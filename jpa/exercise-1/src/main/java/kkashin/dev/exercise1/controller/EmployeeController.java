package kkashin.dev.exercise1.controller;

import jakarta.validation.Valid;
import kkashin.dev.exercise1.mappers.EmployeeDtoDomainMapper;
import kkashin.dev.exercise1.model.dto.CreateEmployeeDto;
import kkashin.dev.exercise1.model.dto.EmployeeDto;
import kkashin.dev.exercise1.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeDtoDomainMapper employeeDtoDomainMapper;
    private final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(
            EmployeeService employeeService,
            EmployeeDtoDomainMapper employeeDtoDomainMapper
    ) {
        this.employeeService = employeeService;
        this.employeeDtoDomainMapper = employeeDtoDomainMapper;
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid CreateEmployeeDto createEmployeeDto) {
        logger.info("POST mapping for createEmployee");

        var employeeToCreate = employeeDtoDomainMapper.toDomain(createEmployeeDto);
        var created = employeeService.createEmployee(employeeToCreate);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(employeeDtoDomainMapper.toDto(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> findEmployeeById(@PathVariable @Valid Long id) {
        logger.info("GET mapping for findEmployeeById");

        var employee = employeeService.getEmployeeById(id);
        var employeeDto = employeeDtoDomainMapper.toDto(employee);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> findAllEmployees() {
        logger.info("GET mapping for findAllEmployees");

        var employeeList = employeeService.getAllEmployees();
        var employeeDtos = employeeList.stream().map(employeeDtoDomainMapper::toDto).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeDtos);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> findEmployeesByDepartment(@RequestParam @Valid String department) {
        logger.info("GET mapping for findEmployeesByDepartment");

        var employeeList = employeeService.getEmployeesByDepartment(department);
        var employeeDtos = employeeList.stream().map(employeeDtoDomainMapper::toDto).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDto> updateEmployeeSalary(
            @PathVariable @Valid Long id,
            @RequestBody @Valid Double salary
    ) {
        logger.info("PUT mapping for updateEmployeeSalary");

        var employee = employeeService.updateEmployeeSalary(id, salary);
        var employeeDto = employeeDtoDomainMapper.toDto(employee);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeDto);
    }

    @GetMapping("/highly-paid")
    public ResponseEntity<List<EmployeeDto>> getHighlyPaidEmployees() {
        logger.info("GET mapping for getHighlyPaidEmployees");

        var employeeList = employeeService.getHighlyPaidEmployees();
        var employeeDtos = employeeList.stream().map(employeeDtoDomainMapper::toDto).toList();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeDtos);
    }
}
