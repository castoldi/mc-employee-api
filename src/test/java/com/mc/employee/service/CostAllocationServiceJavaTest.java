package com.mc.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.util.EmployeeTestFactory;

/**
 * Test cost allocation java calculation implementation.
 */
@ExtendWith(MockitoExtension.class)
class CostAllocationServiceJavaTest {

	@InjectMocks
	private CostAllocationServiceJava costAllocationService;	

	@Mock
	private EmployeeService employeeService;

	@Test
	void testCalculateCostAllocationByDeparment() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(2L, 1L), EmployeeTestFactory.buildManager(1L));

		when(employeeService.findByDepartment(Department.DMP)).thenReturn(employees);

		BigDecimal calculatedValue = costAllocationService.calculateCostAllocationByDeparment(Department.DMP);

		assertEquals(BigDecimal.valueOf(3.3), calculatedValue);
	}

	@Test
	void testCalculateCostAllocationByManager() throws EmployeeNotFoundException {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(2L, 1L), EmployeeTestFactory.buildDeveloper(3L, 1L));

		when(employeeService.findByReportingManager(1L)).thenReturn(employees);

		BigDecimal calculatedValue = costAllocationService.calculateCostAllocationByManager(1L);

		assertEquals(BigDecimal.valueOf(2.2), calculatedValue);
	}

}
