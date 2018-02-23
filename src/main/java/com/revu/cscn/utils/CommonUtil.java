package com.revu.cscn.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CommonUtil {

	static final Logger logger = Logger.getLogger(CommonUtil.class);

	private CommonUtil() {
	}

	public static void writeToFile(String fileName, String data)
			throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		writer.append('\n');
		writer.append(data);
		writer.close();
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public static List<String> readDataFromFile(String inputFilePath)
			throws IOException {
		List<String> rollNoList = new ArrayList<String>();
		FileInputStream excelFile = null;
		Workbook workbook = null;
		try {
			File inputFile = new File(inputFilePath);
			if (inputFile.isFile() && inputFile.canRead()) {
				excelFile = new FileInputStream(inputFile);
				workbook = new XSSFWorkbook(excelFile);
				Sheet datatypeSheet = workbook.getSheetAt(0);
				Iterator<Row> iterator = datatypeSheet.iterator();
				while (iterator.hasNext()) {
					Row currentRow = iterator.next();
					Iterator<Cell> cellIterator = currentRow.iterator();
					while (cellIterator.hasNext()) {
						Cell currentCell = cellIterator.next();
						if (currentCell.getCellTypeEnum() == CellType.STRING) {
							rollNoList.add(currentCell.getStringCellValue());
						}
					}
				}
				logger.error("Total enrty in the input file :: "
						+ rollNoList.size());
			} else {
				logger.error("Application can't read the file...");
			}
		} catch (Exception ex) {
			logger.error("Exception " + ex + " with message " + ex.getMessage());
		} finally {
			if (excelFile != null) {
				excelFile.close();
			}
			if (workbook != null) {
				workbook.close();
			}
		}
		return rollNoList;
	}
}
