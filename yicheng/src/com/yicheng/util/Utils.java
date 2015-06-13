package com.yicheng.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yicheng.common.Config;

public class Utils {
	
	private static Logger logger = LoggerFactory.getLogger(Utils.class);
	
	public static int getRequestIntValue(HttpServletRequest request, String parameterName, boolean throwException) {
		int result = 0;
		String parameterValue = request.getParameter(parameterName);
		if(StringUtils.isBlank(parameterValue)) {
			if(throwException) {
				throw new IllegalArgumentException();
			}else {
				return result;
			}
		}
		
		try {
			result = Integer.parseInt(parameterValue);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			if(throwException) {
				throw e;
			}
		}
		
		return result;
	}

	public static double getRequestDoubleValue(HttpServletRequest request, String parameterName, boolean throwException) {
		double result = 0.0;
		String parameterValue = request.getParameter(parameterName);
		if(StringUtils.isBlank(parameterValue)) {
			if(throwException) {
				throw new IllegalArgumentException();
			}else {
				return result;
			}
		}
		
		try {
			result = Double.parseDouble(parameterValue);
		}catch(NumberFormatException e) {
			logger.error(e.getMessage());
			if(throwException) {
				throw e;
			}
		}
		
		return result;
	}
	
	public static Date parseDate(String dateStr, String format) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.parse(dateStr);
	}
	
	public static String getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if(null == cookies || cookies.length == 0) {
			return null;
		}
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}
	
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	 
	/**
	 * 
	 * 解析Excel文件
	 * 
	 * @param filePath 文件路径
	 * @param columnNum 列数
	 * @param version excel版本 0：98-2003， 1:2007+，默认1
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static List<String[]> parseExcel(String filePath, int columnNum, int version)
			throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}

		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		List<String[]> resultList = new ArrayList<String[]>();
		InputStream input = new FileInputStream(file);
		
		switch (version) {
		case 0:
			workbook = new HSSFWorkbook(input);
			break;
		case 1:
			workbook = new XSSFWorkbook(input);
			break;
		default:
			workbook = new XSSFWorkbook(input);
			break;
		}
		
		int numberOfSheets = workbook.getNumberOfSheets();
		// sheet
		for (int sheetIndex = 0; sheetIndex < numberOfSheets; sheetIndex++) {
			sheet = workbook.getSheetAt(sheetIndex);
			if (null == sheet) {
				continue;
			}
			// row
			int lastRowIndex = sheet.getLastRowNum();
			for (int rowIndex = 1; rowIndex <= lastRowIndex; rowIndex++) {
				row = sheet.getRow(rowIndex);
				if (null == row) {
					continue;
				}
				// cell
				String[] line = new String[columnNum];
				for (int cellIndex = 0; cellIndex < columnNum; cellIndex++) {
					cell = row.getCell(cellIndex);
					if (null == cell) {
						continue;
					}
					line[cellIndex] = getCellValue(cell, true);
				}
				resultList.add(line);
			}
		}
		if(null != input) {
			input.close();
		}
		return resultList;
	}
	
	/**
	 * 
	 * 将数据以Excel形式导出
	 * 
	 * @param columnNames
	 * @param data
	 * @param out
	 * @param version
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void exportExcel(String[] columnNames, String[][] data, OutputStream output, int version)
			throws IOException {
		if(null == output) {
			return ;
		} 
		
		if(null == columnNames || columnNames.length == 0) {
			return ;
		}
		
		if(data == null || data.length == 0) {
			return ;
		}
		
		Workbook workbook = null;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;

		switch (version) {
		case 0:
			workbook = new HSSFWorkbook();
			break;
		case 1:
			workbook = new XSSFWorkbook();
			break;
		default:
			workbook = new XSSFWorkbook();
			break;
		}

		style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		sheet = workbook.createSheet("学籍数据");

		// 表头
		row = sheet.createRow(0);
		for (int i = 0; i < columnNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(style);
		}

		// 写入数据
		for (int i = 0; i < data.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < data[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(data[i][j]);
				cell.setCellStyle(style);
			}
		}

		workbook.write(output);
		
		output.flush();
		output.close();
	}

	private static String getCellValue(Cell cell, boolean intFlag) {
		if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			if (intFlag) {
				int value = (int) cell.getNumericCellValue();
				return String.valueOf(value);
			} else {
				return String.valueOf(cell.getNumericCellValue());
			}
		} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			boolean value = cell.getBooleanCellValue();
			return String.valueOf(value);
		} else {
			return cell.getStringCellValue();
		}
	}
	
	public static String getFileExtensionWithDot(String fileName) {
		if(StringUtils.isBlank(fileName)) {
			return null;
		}
		String[] strArr = fileName.split("\\.");
		if(strArr.length > 1) {
			return "." + strArr[strArr.length - 1];
		}else {
			return null;
		}
		
	}
	
	public static Date convertToDate(String dateString, String dateFormatString) throws ParseException {
		SimpleDateFormat dateFormat = (SimpleDateFormat)DateFormat.getDateInstance();  
		dateFormat.applyPattern(dateFormatString);  
		Date result = dateFormat.parse(dateString);
		return result;
		
	}
	
	public static String[] parseColors(String colorString) {
		if(StringUtils.isBlank(colorString)) {
			return null;
		}
		
		String[] colorArr = colorString.split(Config.COLOR_SEPERATOR);
		return colorArr;
	}
	
}
