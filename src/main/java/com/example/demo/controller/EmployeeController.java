package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource; 

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeExcelService;
import com.example.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeSer;
	
	@Autowired
	EmployeeExcelService excelserve;
	
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
	
	@GetMapping("/search/{empname}")
	public List<Employee> search(@PathVariable("empname") String name) {
		
		return employeeSer.searchbyname(name);
		
	}
	@GetMapping("/search/{role}")
	public List<Employee> searchbyrole(@PathVariable("role") String role) {
		
		return employeeSer.searchbyrole(role);
		
	}
	@GetMapping("/export/excel")
	@ResponseBody
	public ResponseEntity<InputStreamResource> exportToExcel() {
	    ByteArrayInputStream inStream = excelserve.exportToExcel();
        HttpHeaders headers = new HttpHeaders();



        headers.add("Content-Disposition", "attachment; filename=employees.xlsx");  


        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(inStream));
	}
	

	

}
