package com.imaginnovate.emp.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.imaginnovate.emp.entity.Emp;

public interface EmpRepository extends JpaRepository<Emp, Long> {

}
