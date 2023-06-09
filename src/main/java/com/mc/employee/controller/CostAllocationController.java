package com.mc.employee.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.service.CostAllocationService;
import com.mc.employee.service.EmployeeService;
import com.mc.employee.view.CostAllocationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Cost allocation dedicated controller.
 */
@RestController
@RequestMapping("/api/v1/cost")
@RequiredArgsConstructor
public class CostAllocationController {

	private final CostAllocationService costAllocationService;
	private final EmployeeService employeeService;

	@Operation(summary = "Calculate the cost allocation of a Department, disregarding the manager's salary.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Calculated cost allocation") })
	@GetMapping(path = "/department/{department}")
	public CostAllocationResponse calculateCostsByDepartment(@Valid @PathVariable Department department) {
		return CostAllocationResponse.builder()
				.department(department)
				.totalCostByDepartment(costAllocationService.calculateCostAllocationByDeparment(department))
				.build();
	}

	@Operation(summary = "Calculate the cost allocation of a Manager, disregarding the manager's salary.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Calculated cost allocation"), 
			@ApiResponse(responseCode = "404", description = "Manager not found") })
	@GetMapping(path = "/manager/{employeeId}")
	public CostAllocationResponse calculateCostsByManager(@Valid @PathVariable Long employeeId) throws  EmployeeNotFoundException {
		
		Employee manager = employeeService.findById(employeeId);
		
		return CostAllocationResponse.builder()
				.managerId(employeeId)
				.managerName(manager.getName())
				.totalCostByManager(costAllocationService.calculateCostAllocationByManager(employeeId))
				.build();

	}

}
