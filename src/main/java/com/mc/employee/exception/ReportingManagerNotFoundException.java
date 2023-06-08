package com.mc.employee.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class ReportingManagerNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String EMPLOYEE_NOT_FOUND = "Reporting manager id %s not found.";
	private Long employeeId;

	public ReportingManagerNotFoundException(Long employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	public String getMessage() {
		return String.format(EMPLOYEE_NOT_FOUND, employeeId);
	}
}
