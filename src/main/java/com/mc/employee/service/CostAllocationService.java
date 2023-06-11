package com.mc.employee.service;

import java.math.BigDecimal;

import com.mc.employee.entity.Department;
import com.mc.employee.exception.EmployeeNotFoundException;

public interface CostAllocationService {

	BigDecimal calculateCostAllocationByDeparment(Department department);
	
	BigDecimal calculateCostAllocationByManager(Long employeeId) throws EmployeeNotFoundException;
}
