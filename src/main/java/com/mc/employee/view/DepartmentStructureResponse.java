package com.mc.employee.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mc.employee.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentStructureResponse {
	private Map<Department, List<EmployeeView>> departmentStructure = new HashMap<>();
}
