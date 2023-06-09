package com.mc.employee.view;

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
	private EmployeeView employeeView;
}
