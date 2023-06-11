package com.mc.employee.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Persistent Employee object. 
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(indexes = @Index(columnList = "department"))
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

	@Column(unique = true, length = 255)
	@NotNull
	private String email;

	@NotNull
	private BigDecimal salary;

	@NotNull
	private Department department;

	@ManyToOne
	private Employee reportingManager;
}
