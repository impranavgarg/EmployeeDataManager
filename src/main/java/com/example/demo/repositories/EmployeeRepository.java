package com.example.demo.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
@EnableJpaRepositories
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
	
    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByRoleContainingIgnoreCase(String role);
    
    List<Employee> findTop10ByOrderBySalaryDesc();
    
    @Query(value = "select * from employeetable order by salary desc Limit 10", nativeQuery = true)
    List<Employee> findtop10();
    

    
    



}
