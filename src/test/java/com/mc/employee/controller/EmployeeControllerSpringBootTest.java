package com.mc.employee.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.service.EmployeeServiceImpl;
import com.mc.employee.util.EmployeeTestFactory;
import com.mc.employee.view.EmployeeRequest;
import com.mc.employee.view.EmployeeView;

import lombok.SneakyThrows;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeControllerSpringBootTest {

	private static final String EMPLOYEE_BASE_PATH = "/api/v1/employee";
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeServiceImpl employeeService;
	
	@SneakyThrows
	@Test
	void testFindAll() {
		List<Employee> employees = Arrays.asList(EmployeeTestFactory.buildDeveloper(1L, 2L),EmployeeTestFactory.buildDeveloper(3L, 2L));
		when(employeeService.findAll()).thenReturn(employees);
		
		List<EmployeeView> employeesView = Arrays.asList(EmployeeTestFactory.buildDeveloperView(1L, 2L),EmployeeTestFactory.buildDeveloperView(3L, 2L));
		when(employeeService.convertToView(employees)).thenReturn(employeesView);
		
		mockMvc.perform(get(EMPLOYEE_BASE_PATH))
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
		when(employeeService.findById(employee.getId())).thenReturn(employee);
		
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		when(employeeService.convertToView(employee)).thenReturn(employeeView);
		
		mockMvc.perform(get(EMPLOYEE_BASE_PATH + "/" + employee.getId()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(content().string((containsString("Developer 1"))))
				.andExpect(content().string((containsString("Manager 2"))))
				.andExpect(jsonPath("$.*", hasSize(8)));
	}

	@SneakyThrows
	@Test
	void testAddEmployee() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		EmployeeRequest request = EmployeeRequest.builder().employeeView(employeeView).build();
		
		when(employeeService.convertToEmployee(employeeView)).thenReturn(employee);
		when(employeeService.convertToView(employee.getReportingManager())).thenReturn(employeeView);
		when(employeeService.save(employee)).thenReturn(employee);
		
		mockMvc.perform(post(EMPLOYEE_BASE_PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isCreated());
	}

	@SneakyThrows
	@Test
	void testAddEmployeeMissingNameField() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		employeeView.setName(null);
		
		EmployeeRequest request = EmployeeRequest.builder().employeeView(employeeView).build();
		
		when(employeeService.convertToEmployee(employeeView)).thenReturn(employee);
		when(employeeService.convertToView(employee.getReportingManager())).thenReturn(employeeView);
		when(employeeService.save(employee)).thenReturn(employee);
		
		mockMvc.perform(post(EMPLOYEE_BASE_PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$[0].error").exists())
				.andExpect(jsonPath("$[0].error", is("name must not be null")));
	}
	
	@SneakyThrows
	@Test
	void testAddEmployeeDifferentDepartment() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		
		employeeView.getReportingManager().setDepartment(Department.DMP);
		employee.getReportingManager().setDepartment(Department.DMP);
		
		EmployeeRequest request = EmployeeRequest.builder().employeeView(employeeView).build();
		
		when(employeeService.convertToEmployee(employeeView)).thenReturn(employee);
		when(employeeService.findById(employeeView.getReportingManager().getId())).thenReturn(employee.getReportingManager());
		when(employeeService.convertToView(employee.getReportingManager())).thenReturn(employeeView.getReportingManager());
		when(employeeService.save(employee)).thenReturn(employee);
		
		mockMvc.perform(post(EMPLOYEE_BASE_PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$[0].error").exists())
				.andExpect(jsonPath("$[0].error", is("The direct reports of a Manager must belong to the same Department of the Manager")));
	}
	
	@SneakyThrows
	@Test
	void testUpdateEmployee() {
		Employee employee = EmployeeTestFactory.buildDeveloper(1L, 2L);
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		EmployeeRequest request = EmployeeRequest.builder().employeeView(employeeView).build();
		
		when(employeeService.findById(employeeView.getId())).thenReturn(employee);
		when(employeeService.findById(employeeView.getReportingManager().getId())).thenReturn(employee.getReportingManager());
		
		mockMvc.perform(put(EMPLOYEE_BASE_PATH)
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	void testDelete() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(1L, 2L);
		EmployeeRequest request = EmployeeRequest.builder().employeeView(employeeView).build();
		
		when(employeeService.exists(employeeView.getId())).thenReturn(true);
		
		mockMvc.perform(delete(EMPLOYEE_BASE_PATH + "/" + employeeView.getId())
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNoContent());
	}

	@SneakyThrows
	@Test
	void testDeleteEmployeeNotFoundException() {
		EmployeeView employeeView = EmployeeTestFactory.buildDeveloperView(33L, 2L);
		EmployeeRequest request = EmployeeRequest.builder().employeeView(employeeView).build();
		
		when(employeeService.exists(employeeView.getId())).thenReturn(false);
		
		mockMvc.perform(delete(EMPLOYEE_BASE_PATH + "/" + employeeView.getId())
				.content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.error").exists())
				.andExpect(jsonPath("$.error", is("Employee id 33 not found.")));
	}
}