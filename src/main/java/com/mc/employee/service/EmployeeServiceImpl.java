package com.mc.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.mapper.EmployeeMapper;
import com.mc.employee.repository.EmployeeRespository;
import com.mc.employee.view.EmployeeView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRespository repository;
	private final EmployeeMapper mapper;

	@Override
	public Employee save(Employee employee) {
		log.info("Saving employee {}", employee.getEmail());
		
		return repository.save(employee);
	}

	@Override
	public Employee findById(Long employeeId) throws EmployeeNotFoundException {
		log.info("Find employee by id={}", employeeId);
		
		Optional<Employee> employee = repository.findById(employeeId);

		if (employee.isEmpty()) {
			throw EmployeeNotFoundException.builder().employeeId(employeeId).build();
		}

		return employee.get();
	}
	
	@Override
	public List<Employee> findAll() {
		log.info("Find all employees");
		return (List<Employee>) repository.findAll();
	}
	
	@Override
	public List<Employee> findByDepartment(Department department) {
		log.info("Find employees by department={}", department);
		return repository.findByDepartment(department);
	}

	@Override
	public List<Employee> findByReportingManager(Long employeeId) {
		log.info("Find employees by ManagerId={}", employeeId);
		return repository.findByReportingManagerId(employeeId);
	}

	@Override
	public void remove(Long employeeId) {
		log.info("Deleting employee id={}", employeeId);
		repository.deleteById(employeeId);
	}

	@Override
	public boolean exists(Long employeeId) {
		log.info("Checking if employee id={} exists ", employeeId);
		return repository.existsById(employeeId);		
	}

	@Override
	public List<EmployeeView> convertToView(List<Employee> employees) {
		return mapper.mapEmployeeList(employees);
	}

	@Override
	public EmployeeView convertToView(Employee employee) {
		return mapper.mapEmployee(employee);
	}

	@Override
	public Employee convertToEmployee(EmployeeView employeeView) {
		return mapper.mapEmployeeView(employeeView);
	}
}
