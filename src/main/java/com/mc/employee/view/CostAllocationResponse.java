package com.mc.employee.view;

import java.math.BigDecimal;

import com.mc.employee.entity.Department;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostAllocationResponse {
	@Schema(description = "Cost Allocation of a Department.", example = "1111.99", nullable = true)
	private BigDecimal totalCostByDepartment;
	
	@Schema(description = "The department related to the totalCostByDepartment field.", example = "DISPUTE", nullable = true)
	private Department department;
	
	
	@Schema(description = "Cost Allocation of a Manager.", example = "1111.99", nullable = true)
	private BigDecimal totalCostByManager;
	
	@Schema(description = "The manager ID related to the totalCostByManager.", example = "1", nullable = true)
	private Long managerId;
	
	@Schema(description = "The manager name related to the totalCostByManager.", example = "1111.99", nullable = true)
	private String managerName;
}
