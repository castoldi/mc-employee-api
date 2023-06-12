package com.mc.employee.mapper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Employee;
import com.mc.employee.util.EmployeeTestFactory;
import com.mc.employee.view.EmployeeView;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {
	
	private EmployeeMapper mapper = new EmployeeMapper();

	@Test
	void testMapEmployee() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		EmployeeView employeeView = mapper.mapEmployee(employee);
		
		assertThat(employeeView.getId(), is(employee.getId()));
		assertThat(employeeView.getDateOfBirth(), is(employee.getDateOfBirth()));
		assertThat(employeeView.getDepartment(), is(employee.getDepartment()));
		assertThat(employeeView.getEmail(), is(employee.getEmail()));
		assertThat(employeeView.getName(), is(employee.getName()));
		assertThat(employeeView.getRole(), is(employee.getRole()));
		assertThat(employeeView.getSalary(), is(employee.getSalary()));
	}

	@Test
	void testMapEmployeeView() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		
		Employee employee = mapper.mapEmployeeView(employeeView);
		
		assertThat(employee.getId(), is(employeeView.getId()));
		assertThat(employee.getDateOfBirth(), is(employeeView.getDateOfBirth()));
		assertThat(employee.getDepartment(), is(employeeView.getDepartment()));
		assertThat(employee.getEmail(), is(employeeView.getEmail()));
		assertThat(employee.getName(), is(employeeView.getName()));
		assertThat(employee.getRole(), is(employeeView.getRole()));
		assertThat(employee.getSalary(), is(employeeView.getSalary()));
		
		assertThat(employee.getReportingManager().getId(), is(employeeView.getReportingManager().getId()));
		assertThat(employee.getReportingManager().getDateOfBirth(), is(employeeView.getReportingManager().getDateOfBirth()));
		assertThat(employee.getReportingManager().getDepartment(), is(employeeView.getReportingManager().getDepartment()));
		assertThat(employee.getReportingManager().getEmail(), is(employeeView.getReportingManager().getEmail()));
		assertThat(employee.getReportingManager().getName(), is(employeeView.getReportingManager().getName()));
		assertThat(employee.getReportingManager().getRole(), is(employeeView.getReportingManager().getRole()));
		assertThat(employee.getReportingManager().getSalary(), is(employeeView.getReportingManager().getSalary()));
		
	}

	@Test
	void testMapEmployeeList() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L), EmployeeTestFactory.buildDeveloper(3L, 2L));
		employees.forEach(e -> {
			List<Employee> directs = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L), EmployeeTestFactory.buildDeveloper(3L, 2L));
			directs.forEach(d -> d.setReportingManager(null));
			e.setDirectReports(directs);	
		});
		
		List<EmployeeView> employeesView = mapper.mapEmployeeList(employees);
		
		assertThat(employeesView.size(), is(employees.size()));
	}

}
