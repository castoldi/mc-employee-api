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
	public EmployeeView convertEmployee(Employee employee) {
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
				.reportingManager(convertEmployee(employee.getReportingManager()))
				.build();
		}
		
		return info;
	}

	public Employee convertEmployeeInfo(EmployeeView info) {
		Employee employee = null;
		
		if (info != null) {
			employee = Employee.builder()
					.id(info.getId())
					.name(info.getName())
					.salary(info.getSalary())
					.role(info.getRole())
					.dateOfBirth(info.getDateOfBirth())
					.email(info.getEmail())
					.department(info.getDepartment())
					.reportingManager(convertEmployeeInfo(info.getReportingManager()))
					.build();
		}
		
		return employee;
	}

	public List<EmployeeView> convertEmployeeList(List<Employee> employees) {
		return employees.stream()
				.map(this::convertEmployee)
				.toList();
	}
}
