package com.mc.employee.service;

import java.math.BigDecimal;

import com.mc.employee.entity.Department;
import com.mc.employee.exception.EmployeeNotFoundException;

import io.micrometer.tracing.annotation.NewSpan;

public interface CostAllocationService {

	@NewSpan
	BigDecimal calculateCostAllocationByDeparment(Department department);
	
	@NewSpan
	BigDecimal calculateCostAllocationByManager(Long employeeId) throws EmployeeNotFoundException;
}
