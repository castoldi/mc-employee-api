package com.mc.employee.service;

import java.util.List;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.info.EmployeeInfo;

import io.micrometer.tracing.annotation.NewSpan;

public interface EmployeeService {

	@NewSpan
	Employee save(Employee employee);

	@NewSpan
	Employee findById(Long employeeId) throws EmployeeNotFoundException;

	@NewSpan
	List<Employee> findAll();

	@NewSpan
	List<Employee> findByName(String name);

	@NewSpan
	List<Employee> findByDepartment(Department department);

	@NewSpan
	List<Employee> findByReportingManager(Long employeeId);

	void remove(Long employeeId);

	boolean exists(Long employeeId);

	EmployeeInfo convertToInfo(Employee employee);

	Employee convertToEmployee(EmployeeInfo employee);

	List<EmployeeInfo> convertToInfo(List<Employee> employees);
}