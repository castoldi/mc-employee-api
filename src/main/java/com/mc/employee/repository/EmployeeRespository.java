package com.mc.employee.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mc.employee.entity.Department;
import com.mc.employee.entity.Employee;

public interface EmployeeRespository extends CrudRepository<Employee, Long> {

	Page<Employee> findAll(Pageable pageable);

	/**
	 * Find all managers that don't have a reporting manager for a specific
	 * department.
	 */
	List<Employee> findByDepartmentAndReportingManagerIdIsNull(Department department);

	List<Employee> findByDepartment(Department department);

	List<Employee> findByReportingManagerIsNull();

	List<Employee> findByReportingManagerId(Long employeeId);

	List<Employee> findByReportingManagerIdAndDepartment(Long employeeId, Department department);

	@Query(value = "SELECT SUM(salary) FROM Employee e WHERE department=?1")
	Optional<BigDecimal> costAllocationByDepartment(Department department);

	@Query(value = "SELECT SUM(salary) FROM Employee e WHERE e.reportingManager.id=?1")
	Optional<BigDecimal> costAllocationByManagerId(Long employeeId);
}
