package com.example.demo.helper;

import java.io.InputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Employee;

public class Helper {
	
	public static boolean checkedExcelFormat(MultipartFile file) {
		String contentType = file.getContentType();
		
		if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public static List<Employee> convertToEmployeeList(InputStream is){
		
		List<Employee> list = new ArrayList<>();
		try {
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
							emp.setId((long)cell.getNumericCellValue());
							break;
						case 1:
							emp.setName(cell.getStringCellValue());
							break;
						case 2:
							emp.setEmailId(cell.getStringCellValue());
							break;
						case 3:
							emp.setSalary((int)cell.getNumericCellValue());
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
		
		return list;
	}

}
