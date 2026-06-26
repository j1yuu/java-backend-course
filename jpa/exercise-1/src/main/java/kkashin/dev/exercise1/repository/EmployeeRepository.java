package kkashin.dev.exercise1.repository;

import kkashin.dev.exercise1.model.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    List<EmployeeEntity> findByDepartment(String department);
    List<EmployeeEntity> findBySalaryGreaterThan(Double salary);
    List<EmployeeEntity> findByLastNameIgnoreCase(String lastName);

    @Query("select e from EmployeeEntity e where e.salary > (select avg(sub.salary) from EmployeeEntity sub)")
    List<EmployeeEntity> findBySalaryGreaterThanAvg();

    boolean existsByEmail(String email);

    long deleteByDepartment(String department);
}
