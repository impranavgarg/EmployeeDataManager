package com.example.demo.controller;

import java.io.ByteArrayInputStream;
import java.util.*;


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

import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeSer;
	
	@Autowired
	EmployeeExcelService excelserve;
	
	@PostMapping("/create")
	public ResponseEntity<?> saveemp(@RequestBody Employee e) {
    	
		try {
            Employee createdEmployee = employeeSer.createEmployee(e);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        } catch (ValidationException e1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e1.getMessage());
        } catch (Exception e1) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the employee.");
        }

        

		
	}
	@GetMapping("/findtop10")
	public List<Employee> findtop10(){
		return employeeSer.topten();
	}
	
	@GetMapping("/findall")
	public List<Employee> retrivelist(){
		return employeeSer.getAll();
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<?> retrivelistbyid(@PathVariable("id") long eid){
		Employee obj=null;
		Optional<Employee> edata=employeeSer.getEmpbyID(eid);
		if(edata.isPresent()) {
			obj = edata.get();
			return ResponseEntity.status(HttpStatus.FOUND).body(obj);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Do not Exist in DataBase");
		
		
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateemp(@PathVariable("id") long eid ,@RequestBody Employee u) {
		return employeeSer.updateEmployee(eid, u);
		
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteEmp(@PathVariable("id") long eid) {
		
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
	
	@PostMapping("/upload/excel")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
		
		return employeeSer.save(file) ;
	}
	
	
}
