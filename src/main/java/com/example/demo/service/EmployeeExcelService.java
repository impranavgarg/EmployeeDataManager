package com.example.demo.service;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Employee;
import com.example.demo.repositories.EmployeeRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import java.util.*;

@Service
public class EmployeeExcelService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public ByteArrayInputStream exportToExcel() {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {


            Sheet sheet = workbook.createSheet("Employees");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Name");
            headerRow.createCell(2).setCellValue("Email ID");
            headerRow.createCell(3).setCellValue("Salary");
            headerRow.createCell(4).setCellValue("Role");


            List<Employee> employees = employeeRepository.findAll();


            int rowNum = 1;
            for (Employee employee : employees) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getName());
                row.createCell(2).setCellValue(employee.getEmailId());
                row.createCell(3).setCellValue(employee.getSalary());
                row.createCell(4).setCellValue(employee.getRole());
            }


            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                sheet.autoSizeColumn(i); 
            }


            workbook.write(outStream);
            return new ByteArrayInputStream(outStream.toByteArray());
        } catch (IOException ex) {
        	return null;

        }
    }
    
  


}

