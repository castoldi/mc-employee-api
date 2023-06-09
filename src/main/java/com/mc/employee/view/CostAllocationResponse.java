package com.mc.employee.view;

import java.math.BigDecimal;

import com.mc.employee.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostAllocationResponse {
	private BigDecimal totalCostByDepartment;
	private Department department;
	
	private BigDecimal totalCostByManager;
	private Long managerId;
	private String managerName;
}
