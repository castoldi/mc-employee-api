package com.mc.employee.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.EmployeeRole;
import com.mc.employee.validation.SameDepartmentConstraint;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * View Object that is used to send and receive data from the endpoints.   
 */
@SameDepartmentConstraint
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeView {

	@Id
	@GeneratedValue
	private Long id;

	@Schema(description = "Employee name", example = "Firstname Lastname", nullable = false)
	@NotNull
	private String name;
	
	@Schema(description = "Employee role", example = "DEVELOPER", nullable = false)
	private EmployeeRole role;

	@NotNull
	private LocalDate dateOfBirth;

	@Schema(description = "Employee e-mail address", example = "email@mc.com", nullable = false, maxLength = 255)
	@Column(unique = true)
	@Size(max = 255)
	@NotNull
	private String email;

	@Schema(description = "Employee annual base salary", example = "1000000.99", nullable = true)
	@NotNull
	private BigDecimal salary;
	
	@Schema(description = "Employee department", nullable = false)
	@NotNull
	private Department department;

	@Schema(description = "Employee reporting manager. Only field 'id' is required.", nullable = true)
	private EmployeeView reportingManager;
}
