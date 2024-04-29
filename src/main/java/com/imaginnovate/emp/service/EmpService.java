package com.imaginnovate.emp.service;

import java.util.List;
import java.util.Map;

import com.imaginnovate.emp.dto.EmpDto;

public interface EmpService {
	
	public EmpDto createEmployee(EmpDto dto);

	public 	List<EmpDto> GetAllEmployees(int pageno, int psgesize, String sortby, String sortdrc);
	

	Map<String, Object> getEmployeeTaxdeductionCurrentFinancialYear(Long id);
}
