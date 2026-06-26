package kkashin.dev.exercise1.mappers;

import kkashin.dev.exercise1.model.domain.Employee;
import kkashin.dev.exercise1.model.dto.CreateEmployeeDto;
import kkashin.dev.exercise1.model.dto.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDtoDomainMapper {

    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(
                employee.id(),
                employee.firstName(),
                employee.lastName(),
                employee.email(),
                employee.department(),
                employee.salary(),
                employee.hireDate()
        );
    }

    public Employee toDomain(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.id(),
                employeeDto.firstName(),
                employeeDto.lastName(),
                employeeDto.email(),
                employeeDto.department(),
                employeeDto.salary(),
                employeeDto.hireDate()
        );
    }

    public Employee toDomain(CreateEmployeeDto employeeDto) {
        return new Employee(
                null,
                employeeDto.firstName(),
                employeeDto.lastName(),
                employeeDto.email(),
                employeeDto.department(),
                employeeDto.salary(),
                employeeDto.hireDate()
        );
    }
}
