package com.mc.employee.controller;

import java.net.URI;
import java.util.List;

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
import com.mc.employee.validation.EmployeeRequestValidator;
import com.mc.employee.view.EmployeeRequest;
import com.mc.employee.view.EmployeeView;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
	private final EmployeeRequestValidator employeeRequestValidator;

	@GetMapping
	public List<EmployeeView> findAll() {
		log.info("Find all employees.");
		
		List<Employee> employees = employeeService.findAll();
		return employeeService.convertToView(employees);
	}

	@GetMapping(path = "/{employeeId}")
	public EmployeeView findEmployeeById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
		log.info("Find employee by id={}", employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		return employeeService.convertToView(employee);
	}

	@SneakyThrows
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> addEmployee(@RequestBody EmployeeRequest request) {
		log.info("Add a new employee");
		
		EmployeeView employeeView = request.getEmployeeView();
		Employee employee = employeeService.convertToEmployee(employeeView);
		
		completeManagerInfo(employeeView, employee);
		
		employeeRequestValidator.validateEmployeeInfo(employeeView);
		
		employee = employeeService.save(employee);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Completing the manager info in order to run the Same Department validation.
	 */
	private void completeManagerInfo(EmployeeView employeeView, Employee employee) throws EmployeeNotFoundException {
		if (employeeView.getReportingManager() != null) {
			log.info("Completing reporting manager information given the managerId={}", employeeView.getReportingManager().getId());
			Employee manager = employeeService.findById(employeeView.getReportingManager().getId());
			employee.setReportingManager(manager);
			employeeView.setReportingManager(employeeService.convertToView(manager));
		}
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateEmployee(@RequestBody EmployeeRequest request) throws EmployeeNotFoundException {
		log.info("Update an employee’s detail.");
		
		EmployeeView employeeView = request.getEmployeeView();

		Employee employee = employeeService.findById(employeeView.getId());
		Employee manager = employeeService.findById(employeeView.getReportingManager().getId());

		employee.setDateOfBirth(employeeView.getDateOfBirth());
		employee.setDepartment(employeeView.getDepartment());
		employee.setName(employeeView.getName());
		employee.setReportingManager(manager);
		employee.setRole(employeeView.getRole());
		employee.setSalary(employeeView.getSalary());
		employee.setEmail(employeeView.getEmail());

		employeeService.save(employee);
	}


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
