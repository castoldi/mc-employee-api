package com.mc.employee.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible to calculate the total cost of allocation.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CostAllocationServiceImpl implements CostAllocationService {
	private final EmployeeService employeeService;

	@Override
	public BigDecimal calculateCostAllocationByDeparment(Department department) {
		List<Employee> employees = employeeService.findByDepartment(department);
		
		log.info("Calculating Cost Allocation by Department={}, totalEmployees={}", department, employees.size());
		
		return sumSalaries(employees);
	}

	@Override
	public BigDecimal calculateCostAllocationByManager(Long employeeId) throws EmployeeNotFoundException {
		List<Employee> employees = employeeService.findByReportingManager(employeeId);
		
		log.info("Calculating Cost Allocation by ManagerId={}, totalEmployees={}", employeeId, employees.size());
		
		return sumSalaries(employees);
	}

	private BigDecimal sumSalaries(List<Employee> employees) {
		return employees.stream().map(Employee::getSalary).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
