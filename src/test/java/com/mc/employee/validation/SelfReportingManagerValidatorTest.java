package com.mc.employee.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.util.EmployeeTestFactory;
import com.mc.employee.view.EmployeeView;

import jakarta.validation.ConstraintValidatorContext;

@ExtendWith(MockitoExtension.class)
class SelfReportingManagerValidatorTest {

	@InjectMocks
	private SelfReportingManagerValidator validator;

	@Mock
	private ConstraintValidatorContext context;
	
	@Test
	void testIsNotValid() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(2L, 2L);
		
		boolean isValid = validator.isValid(employeeView, context);
		
		assertFalse(isValid);
	}
	
	@Test
	void testIsValid() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(2L, 1L);
		
		boolean isValid = validator.isValid(employeeView, context);
		
		assertTrue(isValid);
	}
	
	@Test
	void testIsValid_NoReportingManager() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(2L);
		
		boolean isValid = validator.isValid(employeeView, context);
		
		assertTrue(isValid);
	}
	
	@Test
	void testIsValid_AddEmployee() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(null, 1L);
		
		boolean isValid = validator.isValid(employeeView, context);
		
		assertTrue(isValid);
	}
}
