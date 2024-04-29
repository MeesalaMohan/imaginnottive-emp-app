package com.imaginnovate.emp.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EmpDto {
	
	private Long id;
	 @NotNull (message = " required firstname ")
	private String firstname;
	@NotNull (message = " required lastname")
	private String lastname;
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	@NotNull (message = " required Email")
	private String email;
	@NotBlank(message = "required Phone no  ")
	private String phonenumber;
	@NotNull(message = " required Date of joining ")
	private LocalDate doj;

	private Double salary;
	
	public EmpDto(Long employeeID, @NotBlank(message = " required firstname ") String firstname,
			@NotBlank(message = " required lastname") String lastname,
			@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") @NotBlank(message = " required Email") String email,
			@NotBlank(message = "required Phone no  ") String phonenumber,
			@NotNull(message = " required Date of joining ") LocalDate dOJ,
			@NotNull(message = "required Salary") Double salary) {
		super();
		id = employeeID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonenumber = phonenumber;
		doj = dOJ;
		this.salary = salary;
	}
	
	@Override
	public String toString() {
		return "EmployeeDto [EmployeeID=" + id + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", email=" + email + ", phonenumber=" + phonenumber + ", DOJ=" + doj + ", Salary=" + salary + "]";
	}
	
	
	
	
	
}
