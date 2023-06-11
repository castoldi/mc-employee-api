package com.mc.employee.service;

import java.math.BigDecimal;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mc.employee.entity.Department;
import com.mc.employee.exception.EmployeeNotFoundException;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible to calculate the total cost of allocation.<br>
 * This is the implementation where the SUM is done in the database.
 */
@Slf4j
@RequiredArgsConstructor
@Profile("!memory-cost-allocation")
@Service
@Observed(name = "cost-allocation-db")
public class CostAllocationServiceDatabase implements CostAllocationService {
	private final EmployeeService employeeService;

	@Override
	public BigDecimal calculateCostAllocationByDeparment(Department department) {
		log.info("Calculating Cost Allocation by Deparment={}", department);
		return employeeService.calculateCostAllocationByDepartment(department);
	}

	@Override
	public BigDecimal calculateCostAllocationByManager(Long employeeId) throws EmployeeNotFoundException {
		log.info("Calculating Cost Allocation by ManagerId={}", employeeId);
		return employeeService.calculateCostAllocationByManagerId(employeeId);
	}
}
