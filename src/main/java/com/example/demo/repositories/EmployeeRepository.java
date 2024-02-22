package com.example.demo.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
    List<Employee> findByNameContaining(String name);



}
