package com.mc.employee.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class EmployeeNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String EMPLOYEE_NOT_FOUND = "Employee id %s not found.";
	private final Long employeeId;

	public EmployeeNotFoundException(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String getMessage() {
		return String.format(EMPLOYEE_NOT_FOUND, employeeId);
	}
}
