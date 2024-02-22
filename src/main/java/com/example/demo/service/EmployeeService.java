package com.example.demo.service;


import java.io.IOException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.helper.Helper;
import com.example.demo.model.Employee;
import com.example.demo.repositories.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	EmployeeRepository emprepo;
	
	
    public Employee createEmployee(Employee e) {
        return emprepo.save(e);
    }
    
    public List<Employee> getAll() {
        return emprepo.findAll();
    }
    public Optional<Employee> getEmpbyID(long id) {
        return emprepo.findById(id);
    }
    
    public Employee updateEmployee(long id, Employee updateemp) {
        Optional<Employee> optionalEmployee = emprepo.findById(id);

        if (optionalEmployee.isPresent()) {
            Employee existemp = optionalEmployee.get();
            existemp.setName(null);
            
            existemp.setEmailId(updateemp.getEmailId());
            existemp.setSalary(updateemp.getSalary());
            existemp.setRole(updateemp.getRole());
            return emprepo.save(existemp);
        } else {
            throw new IllegalArgumentException("Employee with id " + id + " not found"); 
        }
    }
    public String deleteEmp(long id) {
    	Employee obj=null;

		Optional<Employee> edata=emprepo.findById(id);
		if(edata.isPresent()) {
			obj = edata.get();
			emprepo.delete(obj);
			return "Successfully deleted the employee";

		}else {
			return "Employee is not able to delete";
		}
		
		
    }
    
    public List<Employee> searchbyname(String name){
    	return emprepo.findByNameContainingIgnoreCase(name);
    }
    public List<Employee> searchbyrole(String role){
    	return emprepo.findByRoleContainingIgnoreCase(role);
    }
    
    

    
    public void save(MultipartFile file) {
    	try {
			List<Employee> employees = Helper.convertToEmployeeList( file.getInputStream());
			this.emprepo.saveAll(employees);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }


    

    

}
