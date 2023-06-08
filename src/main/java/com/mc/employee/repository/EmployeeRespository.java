package com.mc.employee.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;

public interface EmployeeRespository extends CrudRepository<Employee, Long> {

	List<Employee> findByNameContains(String name);

	List<Employee> findByDepartment(Department department);

	List<Employee> findByReportingManagerId(Long employeeId);
}
