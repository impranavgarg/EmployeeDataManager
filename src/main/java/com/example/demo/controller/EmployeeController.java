package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeSer;
	
	@PostMapping("/create")
	public Employee saveemp(@RequestBody Employee e) {
		employeeSer.createEmployee(e);
		return e;
		
	}
	
	@GetMapping("/findall")
	public List<Employee> retrivelist(){
		return employeeSer.getAll();
	}
	
	@GetMapping("/find/{id}")
	public Employee retrivelistbyid(@PathVariable("id") long eid){
		Employee obj=null;
		Optional<Employee> edata=employeeSer.getEmpbyID(eid);
		if(edata.isPresent()) {
			obj = edata.get();
			return obj;
		}
		
		return obj;
		
		
	}
	
	@PutMapping("/update/{id}")
	public Employee updateemp(@PathVariable("id") long eid ,@RequestBody Employee u) {
		return employeeSer.updateEmployee(eid, u);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteEmp(@PathVariable("id") long eid) {
		
		
		 return employeeSer.deleteEmp(eid);
		
		

		
	}
	

}
