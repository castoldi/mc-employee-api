package com.mc.employee.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.ReportingManagerNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CostAllocationServiceImpl implements CostAllocationService {
	private final EmployeeService employeeService;

	@Override
	public BigDecimal calculateCostAllocationByDeparment(Department department) {
		List<Employee> employees = employeeService.findByDepartment(department);
		return sumSalaries(employees);
	}

	@Override
	public BigDecimal calculateCostAllocationByManager(Long employeeId) throws ReportingManagerNotFoundException {
		List<Employee> employees = employeeService.findByReportingManager(employeeId);
		return sumSalaries(employees);
	}

	private BigDecimal sumSalaries(List<Employee> employees) {
		return employees.stream().map(e -> e.getSalary()).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
