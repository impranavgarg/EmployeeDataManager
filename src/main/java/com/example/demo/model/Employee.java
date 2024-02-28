package com.example.demo.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name="employeetable")
public class Employee {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmailId() {
		return EmailId;
	}
	public void setEmailId(String emailId) {
		EmailId = emailId;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	

	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    @Email 
	private String EmailId;
	@Positive
	private int  salary;
	private String role;


}
