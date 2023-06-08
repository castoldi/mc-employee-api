package com.mc.employee.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	private String name;
	
	@NotNull
	private EmployeeRole role;

	@NotNull
	private LocalDate dateOfBirth;

	@Column(unique = true)
	@NotNull
	private String email;

	@NotNull
	private BigDecimal salary;

	@ManyToOne
	private Employee reportingManager;

	@NotNull
	private Department department;
}
