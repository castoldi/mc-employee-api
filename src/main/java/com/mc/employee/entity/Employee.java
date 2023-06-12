package com.mc.employee.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Persistent Employee object. 
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(indexes = @Index(columnList = "department"))
@ToString(exclude = "directReports")
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

	@Enumerated(EnumType.STRING)
	@NotNull
	private Department department;

	@ManyToOne(cascade = CascadeType.ALL)
	private Employee reportingManager;
	
	@OneToMany(mappedBy = "reportingManager", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Employee> directReports;
}
