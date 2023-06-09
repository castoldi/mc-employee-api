package com.mc.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;

@ExtendWith(MockitoExtension.class)
class CostAllocationServiceTest {

	@InjectMocks
	private CostAllocationServiceImpl costAllocationService;

	@Mock
	private EmployeeService employeeService;

	@Test
	void testCalculateCostAllocationByDeparment() {

		List<Employee> employees = createEmployeesList();

		when(employeeService.findByDepartment(Department.DMP)).thenReturn(employees);

		BigDecimal calculatedValue = costAllocationService.calculateCostAllocationByDeparment(Department.DMP);

		assertEquals(BigDecimal.valueOf(3.3), calculatedValue);
	}

	@Test
	void testCalculateCostAllocationByManager() throws EmployeeNotFoundException {
		List<Employee> employees = createEmployeesList();

		when(employeeService.findByReportingManager(1L)).thenReturn(employees);

		BigDecimal calculatedValue = costAllocationService.calculateCostAllocationByManager(1L);

		assertEquals(BigDecimal.valueOf(3.3), calculatedValue);
	}

	private List<Employee> createEmployeesList() {
		List<Employee> employees = new ArrayList<>();
		employees.add(Employee.builder().salary(BigDecimal.valueOf(1.1)).build());
		employees.add(Employee.builder().salary(BigDecimal.valueOf(2.2)).build());
		return employees;
	}

}
