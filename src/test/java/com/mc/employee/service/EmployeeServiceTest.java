package com.mc.employee.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.mapper.EmployeeMapper;
import com.mc.employee.repository.EmployeeRespository;
import com.mc.employee.util.EmployeeTestFactory;
import com.mc.employee.view.EmployeeView;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

	@Mock
	private EmployeeRespository repository;

	@Mock
	private EmployeeMapper mapper;

	@InjectMocks
	private EmployeeServiceImpl service;

	@Test
	void testSave() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		
		when(repository.save(employee)).thenReturn(employee);
		Employee actual = service.save(employee);
		
		assertThat(employee, is(actual));
	}

	@Test
	void testFindById() throws EmployeeNotFoundException {
		Long employeeId = 1L;
		
		when(repository.findById(1L)).thenReturn(Optional.of(EmployeeTestFactory.buildDeveloper(employeeId, 2L)));
		
		Employee employee = service.findById(1L);
		
		assertThat(employee.getName(), is("Developer 1"));
		
		service.remove(1L);
	}
	
	@Test
	void testFindByIdEmployeeNotFoundException() throws EmployeeNotFoundException {
		Long employeeId = 1L;

		when(repository.findById(employeeId)).thenReturn(Optional.empty());

		EmployeeNotFoundException thrown = assertThrows(EmployeeNotFoundException.class, () -> {
			service.findById(employeeId);
		});

		String expectedErrorMessage = String.format("Employee id %s not found.", employeeId);
		assertEquals(expectedErrorMessage, thrown.getMessage());
	}

	@Test
	void testFindAll() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L), EmployeeTestFactory.buildDeveloper(3L, 4L));
		
		when(repository.findAll()).thenReturn(employees);
		
		List<Employee> actual = service.findAll();
		
		assertThat(actual, notNullValue());
		assertThat(employees.size(), is(actual.size()));
	}

	@Test
	void testFindByDepartment() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L), EmployeeTestFactory.buildDeveloper(3L, 4L));
		
		when(repository.findByDepartment(Department.DMP)).thenReturn(employees);
		
		List<Employee> actual = service.findByDepartment(Department.DMP);
		
		assertThat(actual, notNullValue());
		assertThat(employees.size(), is(actual.size()));		
	}

	@Test
	void testFindByReportingManager() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L), EmployeeTestFactory.buildDeveloper(3L, 2L));
		
		when(repository.findByReportingManagerId(2L)).thenReturn(employees);
		
		List<Employee> actual = service.findByReportingManager(2L);
		
		assertThat(actual, notNullValue());
		assertThat(employees.size(), is(actual.size()));	
	}

	@Test
	void testExists() {
		when(repository.existsById(1L)).thenReturn(true);
		assertThat(service.exists(1L), is(true));
	}

	@Test
	void testNotExists() {
		when(repository.existsById(1L)).thenReturn(false);
		assertThat(service.exists(1L), is(false));
	}

	@Test
	void testConvertToViewListOfEmployee() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L), EmployeeTestFactory.buildDeveloper(3L, 2L));
		List<EmployeeView> employeesView = Arrays.asList(EmployeeTestFactory.buildDeveloperView(1L, 2L), EmployeeTestFactory.buildDeveloperView(3L, 2L));
		
		when(mapper.mapEmployeeList(employees)).thenReturn(employeesView);
		
		List<EmployeeView> actual = service.convertToView(employees);
		assertThat(actual, notNullValue());
		assertThat(employees.size(), is(actual.size()));	
	}

	@Test
	void testConvertToViewEmployee() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		
		when(mapper.mapEmployee(employee)).thenReturn(employeeView);
		
		EmployeeView actual = service.convertToView(employee);
		
		assertThat(actual, notNullValue());
		assertThat(actual, is(employeeView));
	}

	@Test
	void testConvertToEmployee() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		
		when(mapper.mapEmployeeView(employeeView)).thenReturn(employee);
		
		Employee actual = service.convertToEmployee(employeeView);
		
		assertThat(actual, notNullValue());
		assertThat(actual, is(employee));
	}

}
