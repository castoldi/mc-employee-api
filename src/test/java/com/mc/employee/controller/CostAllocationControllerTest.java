package com.mc.employee.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.service.CostAllocationService;
import com.mc.employee.service.EmployeeService;
import com.mc.employee.util.EmployeeTestFactory;

import lombok.SneakyThrows;

@ExtendWith(MockitoExtension.class)
class CostAllocationControllerTest {
	
	private static final String COST_ALLOCATION_BASE_PATH = "/api/v1/cost";
	
	@InjectMocks
	private CostAllocationController controller;
	
	@Mock
	private CostAllocationService costAllocationService;
	
	@Mock
	private EmployeeService employeeService;
	
	private MockMvc mockMvc;	
	private GlobalControllerAdvice globalControllerAdvice = new GlobalControllerAdvice();
	
	@BeforeEach
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(globalControllerAdvice).build();
	}

	@SneakyThrows
	@Test
	void testCalculateCostsByDepartment() {
		when(costAllocationService.calculateCostAllocationByDeparment(Department.DISPUTE)).thenReturn(BigDecimal.valueOf(1.2));
		
		mockMvc.perform(get(COST_ALLOCATION_BASE_PATH + "/department/DISPUTE"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCostByDepartment").exists())
				.andExpect(jsonPath("$.totalCostByDepartment", is(1.2)))
				.andExpect(jsonPath("$.department").exists())
				.andExpect(jsonPath("$.department", is("DISPUTE")));
	}

	@SneakyThrows
	@Test
	void testCalculateCostsByManager() {
		Long managerId = 1L;
		Employee manager = EmployeeTestFactory.buildManager(managerId);
		
		when(employeeService.findById(managerId)).thenReturn(manager);
		
		when(costAllocationService.calculateCostAllocationByManager(managerId)).thenReturn(BigDecimal.valueOf(2.3));
		
		mockMvc.perform(get(COST_ALLOCATION_BASE_PATH + "/manager/" + managerId))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.totalCostByManager").exists())
				.andExpect(jsonPath("$.totalCostByManager", is(2.3)))
				.andExpect(jsonPath("$.managerId").exists())
				.andExpect(jsonPath("$.managerId", is(1)))
				.andExpect(jsonPath("$.managerName").exists())
				.andExpect(jsonPath("$.managerName", is(manager.getName())));
				
	}

}
