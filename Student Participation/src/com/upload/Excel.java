package com.upload;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Part;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;







import DBconnection.DBconnect;

import com.student.Student;

public class Excel {
	
	static DBconnect dao = new DBconnect();
	static Connection con = null;
	
	public String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
	private final static Logger LOGGER = 
            Logger.getLogger(UploadServlet.class.getCanonicalName());
	
	public String getFileType(String fileName){
		
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		
		return extension;
	}
	
	public void uploadFile(Part filePart, String fileType, String subCode, String lecId) throws FileNotFoundException{
		
		// Create path components to save the file
		// final String path = request.getParameter("destination");

		List<Student> studArray = new ArrayList<Student>();		
		
		InputStream filecontent = null;
				
			try {
				
				filecontent = filePart.getInputStream();
				studArray = readStudentFileUpload(filecontent, fileType);
				
				if(!studArray.isEmpty()){
					
					insertStudentFileData(studArray, subCode, lecId);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
			if (filecontent != null) {
				try {
					filecontent.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
} 
	}
	
	public List<Student> readStudentFileUpload(InputStream file, String fileType){
		
		List<Student> studArray = new ArrayList<Student>();
		
		try {
			
			if(fileType.equalsIgnoreCase("xls")){
				
				//Create Workbook instance holding reference to .xlsx file
				HSSFWorkbook xlsbook = new HSSFWorkbook(file);

		        //Get first/desired sheet from the workbook
		        HSSFSheet sheet = xlsbook.getSheetAt(0);
		        
		        //Iterate through each rows one by one
		        Iterator<Row> rowIterator = sheet.iterator();
		        
		     // Decide which rows to process
		        int rowStart = Math.min(10, sheet.getFirstRowNum()+10);
		        int rowEnd = Math.max(1400, sheet.getLastRowNum());
		        
		        System.out.println(rowStart);
		        System.out.println(rowEnd);

		        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
		           Row r = sheet.getRow(rowNum);
		           if (r == null) {
		              // This whole row is empty
		              // Handle it as needed
		              continue;
		           }

		           int lastColumn = Math.max(r.getLastCellNum(), 100);
		           
		           Cell idCell = r.getCell(1);
		           Cell nameCell = r.getCell(2);
		           Student student = new Student();
		           String studId = "";
		          
		           
		           /*
		           for (int cn = 0; cn < lastColumn; cn++) {
		              Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
		              if (c == null) {
		                 // The spreadsheet is empty in this cell
		              } else {
		            */	  	
		            	  switch (idCell.getCellType()) {
		            	  
			                    case Cell.CELL_TYPE_NUMERIC:
			                    	
			                    	
			                    	studId = Double.toString(idCell.getNumericCellValue());
			                    	BigDecimal bd = new BigDecimal(studId);
			                    	long longVal = bd.longValue();
			                    	studId = Long.toString(longVal);
			                    	student.setStudId(studId);
			                        break;
			                        
			                    case Cell.CELL_TYPE_STRING:
			                        
			                        student.setStudId(idCell.getStringCellValue());
			                        break;
			                        
			                }
		            	  
		            	  switch (nameCell.getCellType()) {	
		            	  
			                    case Cell.CELL_TYPE_STRING:
			                    	
			                    	student.setStudName(nameCell.getStringCellValue());			                      
			                        break;
			                }
		            	  
		            	  studArray.add(student);
		            	 
		              }
		              
		           
		      	}else if(fileType.equalsIgnoreCase("xlsx")){
				
					//Create Workbook instance holding reference to .xlsx file
					XSSFWorkbook xlsxbook = new XSSFWorkbook(file);
	
			        //Get first/desired sheet from the workbook
			        XSSFSheet sheet = xlsxbook.getSheetAt(0);
			        
			        //Iterate through each rows one by one
			        Iterator<Row> rowIterator = sheet.iterator();
			        
			     // Decide which rows to process
			        int rowStart = Math.min(10, sheet.getFirstRowNum()+10);
			        int rowEnd = Math.max(1400, sheet.getLastRowNum());
			        
			        System.out.println(rowStart);
			        System.out.println(rowEnd);
	
			        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
			           Row r = sheet.getRow(rowNum);
			           if (r == null) {
			              // This whole row is empty
			              // Handle it as needed
			              continue;
			           }
	
			           int lastColumn = Math.max(r.getLastCellNum(), 100);
	
			           Cell idCell = r.getCell(1);
			           Cell nameCell = r.getCell(2);
			           Student student = new Student();
			           String studId = "";
			          
			           
			           /*
			           for (int cn = 0; cn < lastColumn; cn++) {
			              Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
			              if (c == null) {
			                 // The spreadsheet is empty in this cell
			              } else {
			            */	  	
			            	  switch (idCell.getCellType()) {
			            	  
				                    case Cell.CELL_TYPE_NUMERIC:
				                       
				                    	studId = Double.toString(idCell.getNumericCellValue());
				                    	BigDecimal bd = new BigDecimal(studId);
				                    	long longVal = bd.longValue();
				                    	studId = Long.toString(longVal);
				                    	student.setStudId(studId);
				                        break;
				                        
				                    case Cell.CELL_TYPE_STRING:
				                        
				                        student.setStudId(idCell.getStringCellValue());
				                        break;
				                        
				                }
			            	  
			            	  switch (nameCell.getCellType()) {	
			            	  
				                    case Cell.CELL_TYPE_STRING:
				                    	
				                    	student.setStudName(nameCell.getStringCellValue());			                      
				                        break;
				                }
			            	  
			            studArray.add(student);
			        }
				
				
			}
	            

	        file.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return studArray;
		
	}
	
	public void insertStudentFileData(List<Student> studArray, String subCode, String lecId){
		
		
		con = dao.getConnection();
		
		String sql = "insert into valid_student (stud_name, stud_id, subj_code, lec_id) values (?, ?, ? ,?)";
		
		PreparedStatement stmt = null;
		
		try {
			con.setAutoCommit(false);
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		
		try {
			stmt = con.prepareStatement(sql);
			
			for(Student st:studArray){
				System.out.println(st.getStudId());
				stmt.setString(1, st.getStudName());
				stmt.setString(2, st.getStudId());
				stmt.setString(3, subCode);
				stmt.setString(4, lecId);
				stmt.addBatch();
			}
			
			stmt.executeBatch();
			con.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			dao.closeConnection(con);
			
		}
		
		
		
		
		
	}

}
