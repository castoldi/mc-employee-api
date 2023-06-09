package com.mc.employee.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mc.employee.entity.Employee;
import com.mc.employee.view.EmployeeView;

/**
 * Mapping employee view and entity/model.
 */
@Component
public class EmployeeMapper {
	public EmployeeView mapEmployee(Employee employee) {
		EmployeeView info = null;
		
		if (employee != null) {
			info = EmployeeView.builder()
				.id(employee.getId())
				.name(employee.getName())
				.salary(employee.getSalary())
				.role(employee.getRole())
				.dateOfBirth(employee.getDateOfBirth())
				.email(employee.getEmail())
				.department(employee.getDepartment())
				.reportingManager(mapEmployee(employee.getReportingManager()))
				.build();
		}
		
		return info;
	}

	public Employee mapEmployeeView(EmployeeView view) {
		Employee employee = null;
		
		if (view != null) {
			employee = Employee.builder()
					.id(view.getId())
					.name(view.getName())
					.salary(view.getSalary())
					.role(view.getRole())
					.dateOfBirth(view.getDateOfBirth())
					.email(view.getEmail())
					.department(view.getDepartment())
					.reportingManager(mapEmployeeView(view.getReportingManager()))
					.build();
		}
		
		return employee;
	}

	public List<EmployeeView> mapEmployeeList(List<Employee> employees) {
		return employees.stream()
				.map(this::mapEmployee)
				.toList();
	}
}
