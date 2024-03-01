package com.example.demo.service;



import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helper.Helper;
import com.example.demo.model.Employee;
import com.example.demo.repositories.EmployeeRepository;

import jakarta.validation.ValidationException;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository emprepo;
	
	
    public Employee createEmployee(Employee e) {
    	if (e == null) {
            throw new ValidationException("Employee cannot be null.");
        }
        
    	if (e.getName() == null || e.getName().trim().length() < 1) {
    	    throw new ValidationException("Employee name cannot be empty.");
    	}
       	if(e.getName().length() >15) {
            throw new ValidationException("Name cannot be more than 15 charcters");


    	}
       	e.setName(e.getName().trim());
       	e.setRole(e.getRole().trim());
       	e.setEmailId(e.getEmailId().trim());
       	

       	
        
        return emprepo.save(e);
 
    }
    
    public List<Employee> getAll() {
        return emprepo.findAll();
    }
    public List<Employee> topten(){
    	return emprepo.findtop10();
    }
    public Optional<Employee> getEmpbyID(long id) {
        return emprepo.findById(id);
    }
    
    public ResponseEntity<?> updateEmployee(long id, Employee updateemp) {
        Optional<Employee> optionalEmployee = emprepo.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existemp = optionalEmployee.get();
            updateemp.setName(updateemp.getName().trim());
            updateemp.setRole(updateemp.getRole().trim());
            updateemp.setEmailId(updateemp.getEmailId().trim());
            existemp.setName(updateemp.getName());
            
            existemp.setEmailId(updateemp.getEmailId());
            existemp.setSalary(updateemp.getSalary());
            existemp.setRole(updateemp.getRole());
             return ResponseEntity.status(HttpStatus.FOUND).body("Employee with "+ id+ " is updated in the database");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with "+ id+ " doesnot exist in the database");
        }
    }
    public ResponseEntity<String> deleteEmp(long id) {
    	Employee obj=null;

		Optional<Employee> edata=emprepo.findById(id);
		if(edata.isPresent()) {
			obj = edata.get();
			emprepo.delete(obj);
			return ResponseEntity.status(HttpStatus.FOUND).body("Employee with"+ id+ " this is Deleted from the Database");

		}else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with "+ id+ " doesnot exist in the database");
		}
		
		
    }
    
    public List<Employee> searchbyname(String name){
    	return emprepo.findByNameContainingIgnoreCase(name);
    }
    public List<Employee> searchbyrole(String role){
    	return emprepo.findByRoleContainingIgnoreCase(role);
    }
    
    public ResponseEntity<?> save(MultipartFile file) {
        try {
            ResponseEntity<?> response = Helper.convertToEmployeeList(file.getInputStream());
            if (response.getStatusCode() == HttpStatus.CREATED) {
                List<Employee> employeeList = (List<Employee>) response.getBody();
                if (employeeList != null && !employeeList.isEmpty()) {
                    emprepo.saveAll(employeeList);
                    return ResponseEntity.ok("Employees saved successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No employees found in the file");
                }
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("Error: " + response.getBody());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing file: " + e.getMessage());
        }
    }




}
