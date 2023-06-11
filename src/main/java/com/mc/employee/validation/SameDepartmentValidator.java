package com.mc.employee.validation;

import org.springframework.stereotype.Component;

import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.service.EmployeeService;
import com.mc.employee.view.EmployeeView;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class SameDepartmentValidator implements ConstraintValidator<SameDepartmentConstraint, EmployeeView> {
	private final EmployeeService employeeService;
	
	@Override
	public boolean isValid(EmployeeView employee, ConstraintValidatorContext context) {
		boolean isValid = false;
		
		if (employee.getReportingManager() == null) {
			log.debug("No reporting manager for employee e-mail={}, the same department validation is not required.", employee.getEmail());
			isValid = true;
		} else {
			try {
				Employee manager = employeeService.findById(employee.getReportingManager().getId());
				employee.getReportingManager().setDepartment(manager.getDepartment());
				
				log.debug("Employee department={}, reporting manager department={}", employee.getDepartment(), employee.getReportingManager().getDepartment());
				isValid = employee.getDepartment().equals(employee.getReportingManager().getDepartment());

			} catch (EmployeeNotFoundException e) {
				log.warn("Could not find reporting manager i=" + employee.getReportingManager().getId(), e);
			}			
		}
		
		return isValid;
	}
}
