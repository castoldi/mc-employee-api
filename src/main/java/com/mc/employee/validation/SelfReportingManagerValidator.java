package com.mc.employee.validation;

import org.springframework.stereotype.Component;

import com.mc.employee.view.EmployeeView;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class SelfReportingManagerValidator implements ConstraintValidator<SelfReportingManagerConstraint, EmployeeView> {
	@Override
	public boolean isValid(EmployeeView employee, ConstraintValidatorContext context) {
		boolean isValid = false;
		
		if (employee.getReportingManager() == null) {
			log.debug("No reporting manager for employee e-mail={}, the self reporting manager validation is not required.", employee.getEmail());
			isValid = true;
			
		} else if (employee.getId() == null) {
			log.debug("Employee ID is null, the self reporting manager validation is not required when adding new employees.", employee.getEmail());
			isValid = true;
			
		} else {
			log.info("Validating if the employee reporting manager being updated is not self.");
			isValid = !employee.getId().equals(employee.getReportingManager().getId());
		}
		
		return isValid;
	}
}
