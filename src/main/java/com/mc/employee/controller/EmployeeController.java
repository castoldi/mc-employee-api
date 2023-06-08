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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.info.EmployeeInfo;
import com.mc.employee.info.EmployeeRequest;
import com.mc.employee.service.EmployeeService;
import com.mc.employee.validation.EmployeeRequestValidator;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
	private final EmployeeService employeeService;
	private final EmployeeRequestValidator employeeRequestValidator;

	@GetMapping
	public List<EmployeeInfo> findAll() {
		List<Employee> employees = employeeService.findAll();
		return employeeService.convertToInfo(employees);
	}

	@GetMapping(path = "/{employeeId}")
	public EmployeeInfo findEmployeeById(@PathVariable Long employeeId) throws EmployeeNotFoundException {
		Employee employee = employeeService.findById(employeeId);
		return employeeService.convertToInfo(employee);
	}

	@SneakyThrows
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> addEmployee(@RequestBody EmployeeRequest request) {
		EmployeeInfo employeeInfo = request.getEmployeeInfo();
		
		Employee employee = employeeService.convertToEmployee(employeeInfo);
		if (employeeInfo.getReportingManager() != null) {
			Employee manager = employeeService.findById(employeeInfo.getReportingManager().getId());
			employee.setReportingManager(manager);
			employeeInfo.setReportingManager(employeeService.convertToInfo(manager));
		}
		
		employeeRequestValidator.validateEmployeeInfo(employeeInfo);
		
		employee = employeeService.save(employee);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateEmployee(@RequestBody EmployeeRequest request) throws EmployeeNotFoundException {
		EmployeeInfo employeeInfo = request.getEmployeeInfo();

		Employee employee = employeeService.findById(employeeInfo.getId());
		Employee manager = employeeService.findById(employeeInfo.getReportingManager().getId());

		employee.setDateOfBirth(employeeInfo.getDateOfBirth());
		employee.setDepartment(employeeInfo.getDepartment());
		employee.setName(employeeInfo.getName());
		employee.setReportingManager(manager);
		employee.setRole(employeeInfo.getRole());
		employee.setSalary(employeeInfo.getSalary());
		employee.setEmail(employeeInfo.getEmail());

		employeeService.save(employee);
	}

	@GetMapping("/search")
	public List<EmployeeInfo> search(@RequestParam String name) {
		log.info("Search employee by name");
		List<Employee> employees = employeeService.findByName(name);
		return employeeService.convertToInfo(employees);
	}

	@DeleteMapping(path = "/{employeeId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long employeeId) throws EmployeeNotFoundException {

		if (!employeeService.exists(employeeId)) {
			throw new EmployeeNotFoundException(employeeId);
		}

		employeeService.remove(employeeId);
	}
}
