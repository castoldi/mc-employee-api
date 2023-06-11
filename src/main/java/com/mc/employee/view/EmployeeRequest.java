package com.mc.employee.view;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used to payload create and update employees.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
	@Valid
	private EmployeeView employeeView;
}
