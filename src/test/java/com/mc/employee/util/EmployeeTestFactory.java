package com.mc.employee.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.entity.EmployeeRole;
import com.mc.employee.view.EmployeeView;

public class EmployeeTestFactory {
	
	public static Employee buildDeveloper(Long employeeId, Long managerId) {
		return Employee.builder()
				.name("Developer " + employeeId)
				.id(employeeId)
				.dateOfBirth(LocalDate.of(1981, 1, 1))
				.email("developer" + employeeId + "@mc.com")
				.role(EmployeeRole.DEVELOPER)
				.reportingManager(buildManager(managerId))
				.salary(BigDecimal.valueOf(1.1))
				.department(Department.DISPUTE)
				.build();
	}

	public static Employee buildManager(Long managerId) {
		Employee manager = null;
		
		if (managerId != null) {
			manager = Employee.builder()
						.name("Manager " + managerId)
						.id(managerId)
						.dateOfBirth(LocalDate.of(1981, 1, 1))
						.email("manager" + managerId + "@mc.com")
						.role(EmployeeRole.MANAGER)
						.salary(BigDecimal.valueOf(2.2))
						.department(Department.DISPUTE)
						.build();
		}
		
		return manager;
	}

	/**
	 * Creates employeeView without manager.
	 */
	public static EmployeeView buildDeveloperView(Long employeeId) {
		return buildDeveloperView(employeeId, null);
	}
	
	/**
	 * Creates employee without ID.
	 */
	public static EmployeeView buildDeveloperView() {
		return buildDeveloperView(null, null);
	}


	public static EmployeeView buildDeveloperView(Long employeeId, Long managerId) {
		return EmployeeView.builder()
				.name("Developer " + employeeId)
				.id(employeeId)
				.dateOfBirth(LocalDate.of(1981, 1, 1))
				.email("developer" + employeeId + "@mc.com")
				.role(EmployeeRole.DEVELOPER)
				.reportingManager(buildManagerView(managerId))
				.salary(BigDecimal.valueOf(1.1))
				.department(Department.DISPUTE)
				.build();
	}

	public static EmployeeView buildManagerView(Long managerId) {
		EmployeeView manager = null;
		
		if (managerId != null) {
			manager = EmployeeView.builder()
						.name("Manager " + managerId)
						.id(managerId)
						.dateOfBirth(LocalDate.of(1981, 1, 1))
						.email("manager" + managerId + "@mc.com")
						.role(EmployeeRole.MANAGER)
						.salary(BigDecimal.valueOf(2.2))
						.department(Department.DISPUTE)
						.build();
		}
				
		return manager;
	}
}
