package kkashin.dev.exercise1.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import kkashin.dev.exercise1.mappers.EmployeeDomainEntityMapper;
import kkashin.dev.exercise1.model.domain.Employee;
import kkashin.dev.exercise1.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeDomainEntityMapper employeeDomainEntityMapper;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            EmployeeDomainEntityMapper employeeDomainEntityMapper
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeDomainEntityMapper = employeeDomainEntityMapper;
    }

    public Employee createEmployee(Employee employee) {
        if (employeeRepository.existsByEmail(employee.email()))
            throw new EntityExistsException("Employee with given email already exists: %s".formatted(employee.email()));

        var employeeToSave = employeeDomainEntityMapper.toEntity(employee);
        var createdEmployee = employeeRepository.save(employeeToSave);

        return employeeDomainEntityMapper.toDomain(createdEmployee);
    }

    public Employee getEmployeeById(Long id) {
        var employee = employeeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee with given id was not found: %s".formatted(id))
        );

        return employeeDomainEntityMapper.toDomain(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeDomainEntityMapper::toDomain)
                .toList();
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findByDepartment(department).stream()
                .map(employeeDomainEntityMapper::toDomain)
                .toList();
    }

    public Employee updateEmployeeSalary(Long id, Double salary) {
        var employee = employeeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Employee with given id was not found: %s".formatted(id))
        );

        employee.setSalary(salary);
        var updated = employeeRepository.save(employee);

        return employeeDomainEntityMapper.toDomain(updated);
    }

    public List<Employee> getHighlyPaidEmployees() {
        return employeeRepository.findBySalaryGreaterThanAvg().stream()
                .map(employeeDomainEntityMapper::toDomain)
                .toList();
    }
}
