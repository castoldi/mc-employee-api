package com.mc.employee.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;
import com.mc.employee.exception.EmployeeNotFoundException;
import com.mc.employee.mapper.EmployeeMapper;
import com.mc.employee.repository.EmployeeRespository;
import com.mc.employee.view.DepartmentStructureResponse;
import com.mc.employee.view.EmployeeView;

import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Employee service implementation with relational database.
 */
@Slf4j
@RequiredArgsConstructor
@Observed(name = "employee-service-db")
@Service
public class EmployeeServiceDatabase implements EmployeeService {

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
			throw new EmployeeNotFoundException(employeeId);
		}

		return employee.get();
	}
	
	@Override
	public List<Employee> findAll(int pageNumber, int pageSize) {
		log.info("Find all employees");
		
		Page<Employee> page = repository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "name")));
		
		return page.toList();
	}

	@Override
	public DepartmentStructureResponse listDepartmentStructure(Department department) {
		log.info("List Department structure. (ie. All employees in a hierarchy)");
		DepartmentStructureResponse response = new DepartmentStructureResponse();
		
		response.getDepartmentStructure().put(department, new ArrayList<>());

		List<Employee> departmentManagers = repository.findByDepartmentAndReportingManagerIdIsNull(department);
		
		departmentManagers.forEach(departmentManager -> {
			List<Employee> directReportEmployees = repository.findByReportingManagerId(departmentManager.getId());
			
			EmployeeView managerView = convertToView(departmentManager);
			managerView.setDirectReports(convertToView(directReportEmployees));
			
			//Delete reporting manager from View objects.
			managerView.getDirectReports().forEach(e -> e.setReportingManager(null));
			
			response.getDepartmentStructure().get(department).add(managerView);
		});
		
		return response;
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

	@Override
	public BigDecimal calculateCostAllocationByDepartment(Department department) {
		Optional<BigDecimal> totalCost = repository.costAllocationByDepartment(department);
		
		if (totalCost.isEmpty()) {
			totalCost = Optional.of(BigDecimal.ZERO);
		}
		
		return totalCost.get();
	}

	@Override
	public BigDecimal calculateCostAllocationByManagerId(Long employeeId) {
		Optional<BigDecimal> totalCost = repository.costAllocationByManagerId(employeeId);
		
		if (totalCost.isEmpty()) {
			totalCost = Optional.of(BigDecimal.ZERO);
		}
		
		return totalCost.get();
	}
}
