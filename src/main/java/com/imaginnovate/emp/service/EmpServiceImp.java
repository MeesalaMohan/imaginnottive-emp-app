package com.imaginnovate.emp.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.imaginnovate.emp.dto.EmpDto;
import com.imaginnovate.emp.entity.Emp;
import com.imaginnovate.emp.exception.MyEmployeeException;
import com.imaginnovate.emp.repository.EmpRepository;

@Service
public class EmpServiceImp implements EmpService {

	@Autowired
	private EmpRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public EmpDto createEmployee(EmpDto employeedto) {
		System.out.println(employeedto);
		Emp employee = modelMapper.map(employeedto, Emp.class);
		EmpDto newDto = null;
		Emp savedemployee = null;
		try {
			savedemployee = employeeRepository.save(employee);

		} catch (Exception e) {
			throw new MyEmployeeException("employee allready existed");
		}
		newDto = modelMapper.map(savedemployee, EmpDto.class);
		return newDto;

	}

	@Override
	//
	public List<EmpDto> GetAllEmployees(int pageno, int psgesize, String sortby, String sortdrc) {
		Sort sort = sortdrc.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortby).ascending()
				: Sort.by(sortby).descending();

		Pageable pag = PageRequest.of(pageno, psgesize, sort);
		System.out.println("welcome employeee");

		Page<Emp> allemploylist = employeeRepository.findAll(pag);
		System.out.println("welcome employeee 1" + allemploylist);

		List<EmpDto> allemploydtolist = allemploylist.stream().map((Emp) -> modelMapper.map(Emp, EmpDto.class))
				.collect(Collectors.toList());
		System.out.println("welcome employeee 2" + allemploylist);
		return allemploydtolist;
	}

	@Override
	//
	public Map<String, Object> getEmployeeTaxdeductionCurrentFinancialYear(Long id) {

		Emp employeebyid = employeeRepository.findById(id).get();
		if (employeebyid == null) {
			throw new MyEmployeeException("EmployeeNotFoundException");
		}
		EmpDto employeeDto = modelMapper.map(employeebyid, EmpDto.class);
		LocalDate employeestartingdate = employeeDto.getDoj();
		LocalDate employeeendingdate = LocalDate.now();
		long totalemployeemonthsworking = ChronoUnit.MONTHS.between(employeestartingdate, employeeendingdate);
		Map<String, Object> taxInfo = new HashMap<>();
		taxInfo.put("employeeId", employeebyid.getId());
		taxInfo.put("firstName", employeebyid.getFirstname());
		taxInfo.put("lastName", employeebyid.getLastname());
		double totalsalary = yearlysalary(employeeDto, totalemployeemonthsworking);
		taxInfo.put("yearlySalary", totalsalary);
		taxInfo.put("tax", yearlytax(totalsalary));
		taxInfo.put("cess", getcess(totalsalary));
		return taxInfo;
	}

	public double yearlysalary(EmpDto employeeDto, long totalemployeemonthsworking) {
		Double totalsalaryofemployee = employeeDto.getSalary() * totalemployeemonthsworking;
		return totalsalaryofemployee;
	}

	public double yearlytax(Double yearlySalary) {
		double tax = 0.0;

		if (yearlySalary > 250000) {
			if (yearlySalary <= 500000) {
				tax = (yearlySalary - 250000) * 0.05;
			} else if (yearlySalary <= 1000000) {
				tax = 12500 + (yearlySalary - 500000) * 0.10;
			} else {
				tax = 12500 + 50000 + (yearlySalary - 1000000) * 0.20;
			}
		}

		return tax;
	}

	public double getcess(double totalsalaryofemployee) {
		Double cess = 0.0;
		if (totalsalaryofemployee > 2500000) {
			return cess = (totalsalaryofemployee - 2500000) * 0.02;
		}

		return cess;
	}

	

}
