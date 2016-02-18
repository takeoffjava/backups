package org.strobs.utoo.web.services.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.strobs.utoo.web.services.eClasses.qrcode_users;

public class SimpleExcelReaderExample {
    
   public static void main(String[] args) throws IOException {
	   	String excelFilePath = "E:\\tmp\\Untitled 1.xls";
	    List<qrcode_users> listBooks = readBooksFromExcelFile(excelFilePath);
	    System.out.println(listBooks.get(0).getQrcode_username());
	    System.out.println(listBooks.get(0).getQrcode_mobile_number());
	    System.out.println(listBooks.get(0).getBooked_destination());

   }
   private static Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
   public static List<qrcode_users> readBooksFromExcelFile(String excelFilePath) throws IOException {
	    List<qrcode_users> listBooks = new ArrayList<qrcode_users>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	 //  Workbook workbook = new XSSFWorkbook(inputStream);
	   Workbook workbook = null;
	try {
		workbook = WorkbookFactory.create(inputStream);
	} catch (InvalidFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        qrcode_users aBook = new qrcode_users();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	 
	            switch (columnIndex) {
	            case 0:
	                aBook.setQrcode_username((String) getCellValue(nextCell));
	                break;
	            case 1:
	                aBook.setQrcode_mobile_number((long)((double) getCellValue(nextCell)));
	                break;
	            case 2:
	                aBook.setBooked_destination((String) getCellValue(nextCell));
	                break;
	            }
	 
	        }
	        listBooks.add(aBook);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listBooks;
	}
}