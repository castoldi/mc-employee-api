package com.mc.employee.service;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.mapper.EmployeeMapper;
import com.mc.employee.repository.EmployeeRespository;
import com.mc.employee.util.EmployeeTestFactory;

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
		
		assertThat(employee.getName(), is("Developer"));
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
	void testFindByName() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByDepartment() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByReportingManager() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		fail("Not yet implemented");
	}

	@Test
	void testExists() {
		fail("Not yet implemented");
	}

	@Test
	void testConvertToViewEmployee() {
		fail("Not yet implemented");
	}

	@Test
	void testConvertToEmployee() {
		fail("Not yet implemented");
	}

	@Test
	void testConvertToViewListOfEmployee() {
		fail("Not yet implemented");
	}


}
