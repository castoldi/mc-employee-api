package com.mc.employee.mapper;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.mc.employee.entity.Employee;
import com.mc.employee.view.EmployeeView;

/**
 * Mapping employee view and entity/model.
 */
@Component
public class EmployeeMapper {
	
	public EmployeeView mapEmployee(Employee employee) {
		EmployeeView employeeView = null;
		
		if (employee != null) {
			employeeView = new EmployeeView();
			BeanUtils.copyProperties(employee, employeeView);
			employeeView.setReportingManager(mapEmployee(employee.getReportingManager()));
		}
		
		return employeeView;
	}

	public Employee mapEmployeeView(EmployeeView view) {
		Employee employee = null;
		
		if (view != null) {
			employee = new Employee();
			BeanUtils.copyProperties(view, employee);
			employee.setReportingManager(mapEmployeeView(view.getReportingManager()));
		}
		
		return employee;
	}

	public List<EmployeeView> mapEmployeeList(List<Employee> employees) {
		return employees.stream()
				.map(this::mapEmployee)
				.toList();
	}
}
