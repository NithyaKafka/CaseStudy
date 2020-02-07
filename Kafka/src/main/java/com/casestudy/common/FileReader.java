package com.casestudy.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ResourceUtils;

public class FileReader {

	public static void main(String args[]) throws IOException {
		// readXLSFile();
		getAllCountry();
	}

	public static ArrayList<OnlineRetail> readXLSFile() throws IOException {
		Workbook workbook = null;
		ArrayList<OnlineRetail> retailObjList = null;
		try {
			File file = ResourceUtils.getFile("classpath:Test.xlsx");
			FileInputStream excelFile = new FileInputStream(file);
			workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			retailObjList = new ArrayList<OnlineRetail>();
			int j = 0;
			while (iterator.hasNext()) {
				if (j == 0) {
					j++;
					iterator.next();
					continue;
				}
				OnlineRetail retailObj = new OnlineRetail();
				Row currentRow = iterator.next();
				Cell currentCell = currentRow.getCell(0);
				retailObj.setInvoiceNumber(String.valueOf(currentCell.getNumericCellValue()));

				Cell currentCell1 = currentRow.getCell(1);
				retailObj.setStockCode(String.valueOf(currentCell1.getNumericCellValue()));

				Cell currentCell2 = currentRow.getCell(2);
				retailObj.setDescription(currentCell2.getStringCellValue());

				Cell currentCell3 = currentRow.getCell(3);
				retailObj.setQuantity(currentCell3.getNumericCellValue());

				Cell currentCell4 = currentRow.getCell(4);
				retailObj.setInvoiceDate(currentCell4.getDateCellValue());

				Cell currentCell5 = currentRow.getCell(5);
				retailObj.setUnitPrice(currentCell5.getNumericCellValue());

				Cell currentCell6 = currentRow.getCell(6);
				retailObj.setCustomerID(String.valueOf(currentCell6.getNumericCellValue()));

				Cell currentCell7 = currentRow.getCell(7);
				retailObj.setCountry(currentCell7.getStringCellValue());

				retailObjList.add(retailObj);
			}
			for (OnlineRetail onlineRetail : retailObjList) {
				System.out.println(onlineRetail.getCountry());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return retailObjList;
	}

	public static ArrayList<String> getAllCountry() throws IOException {
		Workbook workbook = null;
		ArrayList<String> retailObjList = null;
		try {

			File file = new File("/home/edyoda/Desktop/Test.xlsx"); // creating a new file instance
			FileInputStream excelFile = new FileInputStream(file);
			workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			retailObjList = new ArrayList<String>();
			int j = 0;
			while (iterator.hasNext()) {
				if (j == 0) {
					j++;
					iterator.next();
					continue;
				}

				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
				int i = 0;
				while (cellIterator.hasNext()) {

					Cell currentCell = cellIterator.next();
					// getCellTypeEnum shown as deprecated for version 3.15
					// getCellTypeEnum ill be renamed to getCellType starting from version 4.0
					if (currentCell.getCellTypeEnum() == CellType.STRING) {
						if (i == 7 && !retailObjList.contains(currentCell.getStringCellValue())) {
							retailObjList.add(currentCell.getStringCellValue());
						}

					}
					i++;

				}

			}
			for (String onlineRetail : retailObjList) {
				System.out.println(onlineRetail);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null)
				workbook.close();
		}
		return retailObjList;
	}
}
