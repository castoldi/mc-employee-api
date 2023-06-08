package com.mc.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mc.employee.entity.Employee;
import com.mc.employee.info.EmployeeInfo;

@Component
public class EmployeeConverter {
	public EmployeeInfo convertEmployee(Employee employee) {
		EmployeeInfo info = null;
		
		if (employee != null) {
			info = EmployeeInfo.builder()
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

	public Employee convertEmployeeInfo(EmployeeInfo info) {
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

	public List<EmployeeInfo> convertEmployeeList(List<Employee> employees) {
		return employees.stream()
				.map(employee -> convertEmployee(employee))
				.collect(Collectors.toList());
	}
}
