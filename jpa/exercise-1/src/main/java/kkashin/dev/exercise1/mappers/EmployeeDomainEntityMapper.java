package kkashin.dev.exercise1.mappers;

import kkashin.dev.exercise1.model.domain.Employee;
import kkashin.dev.exercise1.model.entities.EmployeeEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDomainEntityMapper {
    public Employee toDomain(EmployeeEntity employee) {
        return new Employee(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.getHireDate()
        );
    }

    public EmployeeEntity toEntity(Employee employee) {
        return new EmployeeEntity(
                employee.id(),
                employee.firstName(),
                employee.lastName(),
                employee.email(),
                employee.department(),
                employee.salary(),
                employee.hireDate()
        );
    }
}
