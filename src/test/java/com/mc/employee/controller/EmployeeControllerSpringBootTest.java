package com.mc.employee.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.mc.employee.entity.Employee;
import com.mc.employee.service.EmployeeServiceImpl;
import com.mc.employee.util.EmployeeTestFactory;
import com.mc.employee.view.EmployeeView;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerSpringBootTest {

	private static final String BASE_PATH = "/api/v1";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeServiceImpl service;
	
	@SneakyThrows
	@Test
	void testFindAll() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L),EmployeeTestFactory.buildDeveloper(3L, 2L));
		when(service.findAll()).thenReturn(employees);
		
		List<EmployeeView> employeesView = Arrays.asList(EmployeeTestFactory.buildDeveloperView(1L, 2L),EmployeeTestFactory.buildDeveloperView(3L, 2L));
		when(service.convertToView(employees)).thenReturn(employeesView);
		
		mockMvc.perform(get(BASE_PATH + "/employee"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").exists())
				.andExpect(content().string((containsString("Developer 1"))))
				.andExpect(content().string((containsString("Developer 3"))))
				.andExpect(content().string((containsString("Manager 2"))))
				.andExpect(jsonPath("$.*", hasSize(2)));
				
	}

	@SneakyThrows
	@Test
	void testFindEmployeeById() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		when(service.findById(employee.getId())).thenReturn(employee);
		
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		when(service.convertToView(employee)).thenReturn(employeeView);
		
		mockMvc.perform(get(BASE_PATH + "/employee/" + employee.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(content().string((containsString("Developer 1"))))
				.andExpect(content().string((containsString("Manager 2"))))
				.andExpect(jsonPath("$.*", hasSize(8)));
	}

	@SneakyThrows
	//@Test
	void testAddEmployee() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		when(service.findById(employee.getId())).thenReturn(employee);
		
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		when(service.convertToView(employee)).thenReturn(employeeView);
		
		mockMvc.perform(post(BASE_PATH + "/employee")
				.content("{\r\n"
						+ "	\"employeeView\": {\r\n"
						+ "		\"name\": \"Manager 1\",\r\n"
						+ "		\"role\": \"MANAGER\",\r\n"
						+ "		\"dateOfBirth\": \"1981-01-01\",\r\n"
						+ "		\"email\": \"manager2@mc.com\",\r\n"
						+ "		\"salary\": 1000000.99,\r\n"
						+ "		\"department\": \"DMP\"\r\n"
						+ "	}\r\n"
						+ "}")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@Test
	void testUpdateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testEmployeeController() {
		fail("Not yet implemented");
	}

}
