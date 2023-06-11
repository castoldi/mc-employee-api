package com.mc.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Department;
import com.mc.employee.exception.EmployeeNotFoundException;

/**
 * Test allocation using database implementation.
 */
@ExtendWith(MockitoExtension.class)
class CostAllocationServiceDatabaseTest {

	@InjectMocks
	private CostAllocationServiceDatabase costAllocationService;

	@Mock
	private EmployeeService employeeService;

	@Test
	void testCalculateCostAllocationByDeparment() {
		when(employeeService.calculateCostAllocationByDepartment(Department.DMP)).thenReturn(BigDecimal.valueOf(3.3));

		BigDecimal calculatedValue = costAllocationService.calculateCostAllocationByDeparment(Department.DMP);

		assertEquals(BigDecimal.valueOf(3.3), calculatedValue);
	}

	@Test
	void testCalculateCostAllocationByManager() throws EmployeeNotFoundException {
		when(employeeService.calculateCostAllocationByManagerId(1L)).thenReturn(BigDecimal.valueOf(2.2));

		BigDecimal calculatedValue = costAllocationService.calculateCostAllocationByManager(1L);

		assertEquals(BigDecimal.valueOf(2.2), calculatedValue);
	}
}
