package com.mc.employee.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mc.employee.entity.Department;
import com.mc.employee.exception.ReportingManagerNotFoundException;
import com.mc.employee.service.CostAllocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cost")
@RequiredArgsConstructor
public class CostAllocationController {

	private final CostAllocationService costAllocationService;

	@GetMapping(path = "/department/{department}")
	public BigDecimal calculateCostsByDepartment(@Valid @PathVariable Department department) {
		return costAllocationService.calculateCostAllocationByDeparment(department);
	}

	@Operation(summary = "Total salary costs of a manager, disregarding the manager's salary.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cost allocation calculated"),
			@ApiResponse(responseCode = "404", description = "Manager not found" )})
	@GetMapping(path = "/manager/{employeeId}")
	public BigDecimal calculateCostsByManager(@Valid @PathVariable Long employeeId) throws ReportingManagerNotFoundException {
		return costAllocationService.calculateCostAllocationByManager(employeeId);
	}

}
