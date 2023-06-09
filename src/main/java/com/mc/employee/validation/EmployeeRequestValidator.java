package com.mc.employee.validation;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.mc.employee.controller.EmployeeController;
import com.mc.employee.view.EmployeeView;
import com.mc.employee.view.EmployeeRequest;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class EmployeeRequestValidator {

	public void validateEmployeeInfo(EmployeeView input) throws MethodArgumentNotValidException, NoSuchMethodException, SecurityException {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		BeanPropertyBindingResult result = new BeanPropertyBindingResult(input, "employeeView");
		SpringValidatorAdapter adapter = new SpringValidatorAdapter(validator);
		adapter.validate(input, result);

		if (result.hasErrors()) {
			 MethodParameter methodParameter = new MethodParameter(EmployeeController.class.getMethod("addEmployee", EmployeeRequest.class), 0);
			throw new MethodArgumentNotValidException(methodParameter, result);
		}
	}
}
