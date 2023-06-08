package com.mc.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.info.EmployeeInfo;
import com.mc.employee.repository.EmployeeRespository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRespository repository;
	private final EmployeeConverter converter;

	@Override
	public Employee save(Employee employee) {
		return repository.save(employee);
	}

	@Override
	public Employee findById(Long employeeId) throws EmployeeNotFoundException {
		Optional<Employee> employee = repository.findById(employeeId);

		if (employee.isEmpty()) {
			throw EmployeeNotFoundException.builder().employeeId(employeeId).build();
		}

		return employee.get();
	}
	
	@Override
	public List<Employee> findAll() {
		return (List<Employee>) repository.findAll();
	}
	
	@Override
	public List<Employee> findByName(String name) {
		return repository.findByNameContains(name);
	}

	@Override
	public List<EmployeeInfo> convertToInfo(List<Employee> employees) {
		return converter.convertEmployeeList(employees);
	}

	@Override
	public List<Employee> findByDepartment(Department department) {
		return repository.findByDepartment(department);
	}

	@Override
	public List<Employee> findByReportingManager(Long employeeId) {
		return repository.findByReportingManagerId(employeeId);
	}

	@Override
	public void remove(Long employeeId) {
		repository.deleteById(employeeId);
	}

	@Override
	public boolean exists(Long employeeId) {
		return repository.existsById(employeeId);		
	}
	
	@Override
	public EmployeeInfo convertToInfo(Employee employee) {
		return converter.convertEmployee(employee);
	}

	@Override
	public Employee convertToEmployee(EmployeeInfo employee) {
		return converter.convertEmployeeInfo(employee);
	}
}
