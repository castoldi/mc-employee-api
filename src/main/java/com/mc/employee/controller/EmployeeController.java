package com.mc.employee.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.service.EmployeeService;
import com.mc.employee.view.EmployeeRequest;
import com.mc.employee.view.EmployeeView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Employee  controller. Used to create, update, remove and view employees.
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeService employeeService;

	@Operation(summary = "List Department structure. (ie. All employees in a hierarchy).")
	@GetMapping
	public List<EmployeeView> findAll() {
		log.info("Find all employees.");
		
		List<Employee> employees = employeeService.findAll();
		return employeeService.convertToView(employees);
	}

	@Operation(summary = "Get employee info.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "The employee was found."),
			@ApiResponse(responseCode = "404", description = "The employee was not found.") })
	@GetMapping(path = "/{employeeId}")
	public EmployeeView findEmployeeById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
		log.info("Find employee by id={}", employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		return employeeService.convertToView(employee);
	}

	@Operation(summary = "Add a new employee.")
	@ApiResponses(value = {@ApiResponse(responseCode = "201", description = "The employee was added successfully.")})
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> addEmployee(@Valid @RequestBody EmployeeRequest request) {
		log.info("Add a new employee");
		
		EmployeeView employeeView = request.getEmployeeView();
		Employee employee = employeeService.convertToEmployee(employeeView);
		
		employee = employeeService.save(employee);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Update an employee’s detail.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "The employee record was updated successfully."),
			@ApiResponse(responseCode = "404", description = "The employee was not found.") })
	@PutMapping
	@ResponseStatus(code = HttpStatus.OK)
	public EmployeeView updateEmployee(@Valid @RequestBody EmployeeRequest request) throws EmployeeNotFoundException {
		log.info("Update an employee’s detail for employeeId={}", request.getEmployeeView().getId());
		
		EmployeeView employeeView = request.getEmployeeView();

		Employee employee = employeeService.findById(employeeView.getId());
		Employee manager = employeeService.findById(employeeView.getReportingManager().getId());
		
		BeanUtils.copyProperties(employeeView, employee, "id", "reportingManager");
		employee.setReportingManager(manager);

		employee = employeeService.save(employee);
		
		return employeeService.convertToView(employee);
	}

	@Operation(summary = "Remove an employee.")
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "204", description = "The employee was removed successfully."),
			@ApiResponse(responseCode = "404", description = "The employee was not found.") })
	@DeleteMapping(path = "/{employeeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long employeeId) throws EmployeeNotFoundException {
		log.info("Remove an employee id={}", employeeId);

		if (!employeeService.exists(employeeId)) {
			throw new EmployeeNotFoundException(employeeId);
		}

		employeeService.remove(employeeId);
	}
}
