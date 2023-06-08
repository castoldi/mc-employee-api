package com.mc.employee.validation;

import com.mc.employee.info.EmployeeInfo;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SameDepartmentValidator implements ConstraintValidator<SameDepartmentConstraint, EmployeeInfo> {
	@Override
	public void initialize(SameDepartmentConstraint constraintAnnotation) {
	}

	@Override
	public boolean isValid(EmployeeInfo employee, ConstraintValidatorContext context) {
		
		if (employee.getDepartment() == null || employee.getReportingManager() == null) {
			return true;
		}
		
		log.debug("Employee department={}, reporting manager department={}", employee.getDepartment(), employee.getReportingManager().getDepartment());

		return employee.getDepartment().equals(employee.getReportingManager().getDepartment());
	}
}
