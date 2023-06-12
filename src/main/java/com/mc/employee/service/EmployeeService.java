package com.mc.employee.service;

import java.math.BigDecimal;
import java.util.List;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.view.DepartmentStructureResponse;
import com.mc.employee.view.EmployeeView;

public interface EmployeeService {

	Employee save(Employee employee) throws EmployeeNotFoundException;

	Employee findById(Long employeeId) throws EmployeeNotFoundException;

	List<Employee> findAll(int pageNumber, int pageSize);
	
	DepartmentStructureResponse listDepartmentStructure(Department department);

	List<Employee> findByDepartment(Department department);

	List<Employee> findByReportingManager(Long employeeId);

	void remove(Long employeeId);

	boolean exists(Long employeeId);

	EmployeeView convertToView(Employee employeeView);

	Employee convertToEmployee(EmployeeView employeeView);

	List<EmployeeView> convertToView(List<Employee> employees);
	
	BigDecimal calculateCostAllocationByDepartment(Department department);

	BigDecimal calculateCostAllocationByManagerId(Long employeeId);
}