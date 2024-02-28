package com.example.demo.helper;

import java.io.InputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Employee;
import com.example.demo.repositories.EmployeeRepository;

@Service
public class Helper {
	@Autowired
	EmployeeRepository emprepo;
	
	
	public static boolean checkedExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		
		if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static ResponseEntity<?> convertToEmployeeList(InputStream is){
		
		
		
		List<Employee> list = new ArrayList<>();
		try  {
			XSSFWorkbook workbook = new XSSFWorkbook(is);
			XSSFSheet sheet = workbook.getSheet("Employees");
			int rownum = 0;
			Iterator<Row> iterator = sheet.iterator();
			
			
			while(iterator.hasNext()) {
				Row row = iterator.next();
				if(rownum==0) {
					rownum++;
					continue;
					
				}
				
				Iterator<Cell> cells = row.iterator();
				int cid = 0;
				Employee emp = new Employee();
				while(cells.hasNext()) {
					Cell cell = cells.next();
					
					switch (cid) {
						case 0:
							if (cell.getCellType() == CellType.NUMERIC) {
			                    emp.setId((long) cell.getNumericCellValue());
			                } else {
			                     
			                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id should be Numbers");
			                }
							break;
						case 1:
							emp.setName(cell.getStringCellValue());
							break;
						case 2:
							emp.setEmailId(cell.getStringCellValue());
							break;
						case 3:
							if (cell.getCellType() == CellType.NUMERIC) {
								emp.setSalary((int)cell.getNumericCellValue());
							}else {
			                     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("salary should be Numbers");
							}
							break;
						case 4:
							emp.setRole(cell.getStringCellValue());
							break;
						default:
							break;
					}
					cid++;
					
				}
				list.add(emp);
				
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
        return ResponseEntity.status(HttpStatus.CREATED).body(list);

	}

}
