package com.mc.employee.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.service.EmployeeServiceImpl;
import com.mc.employee.util.EmployeeTestFactory;
import com.mc.employee.view.EmployeeView;

import jakarta.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class SameDepartmentValidatorTest {
	
	@InjectMocks 
	private SameDepartmentValidator sameDepartmentValidator;
	
	@Mock
	private EmployeeServiceImpl employeeService;
	
	@Mock
	private ConstraintValidatorContext context;
	
	@SneakyThrows
	@Test
	void testIsValid() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(2L, 1L);
		Employee employee = EmployeeTestFactory.buildDeveloper(2L, 1L);
		
		when(employeeService.findById(employeeView.getReportingManager().getId())).thenReturn(employee.getReportingManager());
		
		boolean isValid = sameDepartmentValidator.isValid(employeeView, context);
		
		assertTrue(isValid);
	}

	@SneakyThrows
	@Test
	void testIsValid_EmployeeNotFoundException() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(2L, 1L);

		when(employeeService.findById(employeeView.getReportingManager().getId())).thenThrow(new EmployeeNotFoundException(employeeView.getReportingManager().getId()));

		boolean isValid = sameDepartmentValidator.isValid(employeeView, context);

		assertFalse(isValid);
	}

	@SneakyThrows
	@Test
	void testIsValid_NullReportingManager() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(2L, 1L);
		employeeView.setReportingManager(null);

		boolean isValid = sameDepartmentValidator.isValid(employeeView, context);

		assertTrue(isValid);
	}
}
