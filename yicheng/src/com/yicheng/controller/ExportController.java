package com.yicheng.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yicheng.common.MaterialType;
import com.yicheng.pojo.Cloth;
import com.yicheng.pojo.ClothColor;
import com.yicheng.pojo.ClothSize;
import com.yicheng.service.ClothColorService;
import com.yicheng.service.ClothMaterialService;
import com.yicheng.service.ClothService;
import com.yicheng.service.ClothSizeService;
import com.yicheng.service.OrderClothService;
import com.yicheng.service.data.ClothDetailData;
import com.yicheng.service.data.ClothMaterialDetailData;
import com.yicheng.service.data.ClothOrderDetailData;
import com.yicheng.util.GenericResult;
import com.yicheng.util.ResultCode;
import com.yicheng.util.Utils;

@Controller
public class ExportController {

	private static Logger logger = LoggerFactory
			.getLogger(ExportController.class);

	@Autowired
	private ClothService clothService;

	@Autowired
	private ClothMaterialService clothMaterialService;

	@Autowired
	private ClothColorService clothColorService;
	
	@Autowired
	private ClothSizeService clothSizeService;
	
	@Autowired
	private OrderClothService orderClothService;

	@RequestMapping(value = { "/Manager/ExportMaterialExcel",
			"/Proofing/ExportMaterialExcel" }, method = RequestMethod.GET)
	public void exportMaterial(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);

		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		String fileName = null;
		if (clothResult.getResultCode() == ResultCode.NORMAL) {
			fileName = clothResult.getData().getType() + "_ " + "面辅料明细表";
		} else {
			fileName = "面辅料明细表";
		}

		fileName = new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1");
		setResponseInfo(response, fileName);

		Workbook workbook = new HSSFWorkbook();

		GenericResult<List<ClothColor>> clothColorResult = clothColorService
				.getByCloth(clothId);
		if (clothColorResult.getResultCode() == ResultCode.NORMAL) {
			for (ClothColor clothColor : clothColorResult.getData()) {
				GenericResult<List<ClothMaterialDetailData>> leathherResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_LEATHER);
				GenericResult<List<ClothMaterialDetailData>> fabricResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_FABRIC);
				GenericResult<List<ClothMaterialDetailData>> supportResults = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_SUPPORT);

				exportClothMaterialSheet(workbook, clothColor.getColor(),
						clothResult.getData(), leathherResult.getData(),
						fabricResult.getData(), supportResults.getData());
			}
		}

		OutputStream output = response.getOutputStream();
		workbook.write(output);

		output.flush();
		output.close();
	}

	@RequestMapping(value = { "/Manager/ExportPriceExcel",
			"/Pricing/ExportPriceExcel" }, method = RequestMethod.GET)
	public void exportPrice(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
 		int clothId = Utils.getRequestIntValue(request, "clothId", true);

		GenericResult<Cloth> clothResult = clothService.getById(clothId);
		String fileName = null;
		if (clothResult.getResultCode() == ResultCode.NORMAL) {
			fileName = clothResult.getData().getType() + "_ " + "报价单";
		} else {
			fileName = "报价单";
		}

		fileName = new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1");
		setResponseInfo(response, fileName);

		Workbook workbook = new HSSFWorkbook();

		GenericResult<List<ClothColor>> clothColorResult = clothColorService
				.getByCloth(clothId);
		if (clothColorResult.getResultCode() == ResultCode.NORMAL) {
			for (ClothColor clothColor : clothColorResult.getData()) {
				GenericResult<List<ClothMaterialDetailData>> leathherResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_LEATHER);
				GenericResult<List<ClothMaterialDetailData>> fabricResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_FABRIC);
				GenericResult<List<ClothMaterialDetailData>> supportResults = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_SUPPORT);

				exportPriceCountSheet(workbook, clothColor.getColor(),
						clothResult.getData(), leathherResult.getData(),
						fabricResult.getData(), supportResults.getData());
			}
		}

		OutputStream output = response.getOutputStream();
		workbook.write(output);

		output.flush();
		output.close();
	}

	@RequestMapping(value = { "/Manager/ExportCountExcel",
			"/Buyer/ExportCountExcel" }, method = RequestMethod.GET)
	public void exportCount(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		int clothId = Utils.getRequestIntValue(request, "clothId", true);

		GenericResult<Cloth> clothResult = clothService.getById(clothId);

		String fileName = null;
		if (clothResult.getResultCode() == ResultCode.NORMAL) {
			fileName = clothResult.getData().getType() + "_ " + "采购表";
		} else {
			fileName = "采购表";
		}
		
		fileName = new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1");
		setResponseInfo(response, fileName);

		Workbook workbook = new HSSFWorkbook();
		
		GenericResult<List<ClothColor>> clothColorResult = clothColorService
				.getByCloth(clothId);
		if (clothColorResult.getResultCode() == ResultCode.NORMAL) {
			for (ClothColor clothColor : clothColorResult.getData()) {
				GenericResult<List<ClothMaterialDetailData>> leathherResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_LEATHER);
				GenericResult<List<ClothMaterialDetailData>> fabricResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_FABRIC);
				GenericResult<List<ClothMaterialDetailData>> supportResult = clothMaterialService
						.getTypeDetailByCloth(clothId, clothColor.getId(),
								MaterialType.MATERIAL_TYPE_SUPPORT);
				
				GenericResult<List<ClothSize>> clothSizeResult = clothSizeService.getByClothColor(clothId, clothColor.getId());
				GenericResult<ClothOrderDetailData> clothOrderDetailResult = orderClothService
						.search(null, clothId);
				
				exportClothCountSheet(workbook, clothColor.getColor(), clothResult.getData(), clothOrderDetailResult.getData(),
						clothSizeResult.getData(), leathherResult.getData(),
						fabricResult.getData(), supportResult.getData());
			}
		}


		OutputStream output = response.getOutputStream();
		workbook.write(output);

		output.flush();
		output.close();

	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = { "/Manager/ExportRecordItems", "/Proofing/ExportRecordItems" }, method = RequestMethod.GET)
public void exportRecordItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
	String[] selectedIds = request.getParameterValues("selectedId");
	List<Integer> clothIds = new ArrayList<Integer>();
	if(null == selectedIds || selectedIds.length == 0) {
		return;
	}
	
	for(String selectIdString : selectedIds) {
		try {
			clothIds.add(Integer.parseInt(selectIdString));
		} catch (NumberFormatException e) {
			continue;
		}
	}

	if(!clothIds.isEmpty()) {
		String fileName = "历史记录";
	
		fileName = new String((fileName + ".xls").getBytes("GBK"), "ISO8859_1");
		setResponseInfo(response, fileName);
	
		Workbook workbook = new HSSFWorkbook();
		
		String[] columns = new String[]{"款号", "款名", "颜色", "买手", "供应商", "创建时间"};

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		CellStyle colorStyle = null;

		if (workbook instanceof HSSFWorkbook) {
			HSSFColor lightGray = setColor((HSSFWorkbook) workbook,
					(byte) 0xE0, (byte) 0xE0, (byte) 0xE0);
			colorStyle = workbook.createCellStyle();
			colorStyle.setFillForegroundColor(lightGray.getIndex());
			colorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}

		style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		sheet = workbook.createSheet("历史记录");

		int rowIndex = 0;
		row = sheet.createRow(rowIndex);
		for (int i = 0; i < columns.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(colorStyle);
		}
		rowIndex++;
		
		for(int clothId : clothIds) {
			
			GenericResult<Cloth> clothResult = clothService.getById(clothId);
			GenericResult<List<ClothColor>> clothColorResult = clothColorService.getByCloth(clothId);
			
			if(clothResult.getResultCode() == ResultCode.NORMAL && clothColorResult.getResultCode() == ResultCode.NORMAL) {
				row = sheet.createRow(rowIndex);
				ClothDetailData cloth = new ClothDetailData(clothResult.getData(), clothColorResult.getData());
				
				cell = row.createCell(0);
				cell.setCellValue(cloth.getType());
				cell.setCellStyle(style);
				
				cell = row.createCell(1);
				cell.setCellValue(cloth.getName());
				cell.setCellStyle(style);
				
				cell = row.createCell(2);
				cell.setCellValue(cloth.getColor());
				cell.setCellStyle(style);
				
				cell = row.createCell(3);
				cell.setCellValue(cloth.getClient());
				cell.setCellStyle(style);
				
				cell = row.createCell(4);
				cell.setCellValue(null == cloth.getCreatedTime() ? "" : cloth.getCreatedTime().toLocaleString());
				cell.setCellStyle(style);
				
				rowIndex++;
			}
			
		}
		
	
		OutputStream output = response.getOutputStream();
		workbook.write(output);
		
		output.flush();
		output.close();
	}

}
	
	@SuppressWarnings("deprecation")
	private void exportClothCountSheet(Workbook workbook, String sheetTitle,
			Cloth cloth, ClothOrderDetailData clothOrder, List<ClothSize> clothSizes, List<ClothMaterialDetailData> leatherList,
			List<ClothMaterialDetailData> fabricList,
			List<ClothMaterialDetailData> supportList) {
		
		String[] clothSizeColumns = new String[] {"XS", "S", "M", "L", "XL", "XXL", "小计"};
		String[] columnNames = new String[] { "项目", "部位", "单位", "用量", "数量",
				"单价", "金额", "订购日期", "备注" };

		String[][] leatherData = getCountTableData(leatherList,
				columnNames.length);
		String[][] fabricData = getCountTableData(fabricList,
				columnNames.length);
		String[][] supportData = getCountTableData(supportList,
				columnNames.length);

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		CellStyle colorStyle = null;

		if (workbook instanceof HSSFWorkbook) {
			HSSFColor lightGray = setColor((HSSFWorkbook) workbook,
					(byte) 0xE0, (byte) 0xE0, (byte) 0xE0);
			colorStyle = workbook.createCellStyle();
			colorStyle.setFillForegroundColor(lightGray.getIndex());
			colorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}

		style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		sheet = workbook.createSheet(sheetTitle);

		int rowIndex = 0;
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("款号");
		cell = row.createCell(1);
		cell.setCellValue(cloth.getType());
		rowIndex++;

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("款名");
		cell = row.createCell(1);
		cell.setCellValue(cloth.getName());
		rowIndex++;
		
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("交期");
		cell = row.createCell(1);
		cell.setCellValue(null == clothOrder.getDeliveryDate() ? "" : clothOrder.getDeliveryDate().toLocaleString());
		rowIndex++;

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("总数");
		cell = row.createCell(1);
		cell.setCellValue(null == clothOrder.getCount() ? "" : clothOrder.getCount().toString());
		rowIndex++;
		
		row = sheet.createRow(rowIndex);
		for (int i = 0; i < clothSizeColumns.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(clothSizeColumns[i]);
			cell.setCellStyle(style);
		}
		rowIndex++;
		
		if(null != clothSizes && !clothSizes.isEmpty()) {
			row = sheet.createRow(rowIndex);
			int clothSizeLength = clothSizes.size();
			for(int i = 0; i < clothSizeLength; i++) {
				cell = row.createCell(i);
				cell.setCellValue(clothSizes.get(i).getCount() + "");
				cell.setCellStyle(style);
			}
			
			int colorSizeTotal = 0;
			for(int i = 0; i < clothSizeLength; i++) {
				colorSizeTotal += clothSizes.get(i).getCount();
			}
			cell = row.createCell(clothSizeLength);
			cell.setCellValue(colorSizeTotal);
			cell.setCellStyle(style);
			rowIndex++;
		}else {
			row = sheet.createRow(rowIndex);
			for (int i = 0; i < clothSizeColumns.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue("暂无");
				cell.setCellStyle(style);
			}
			rowIndex++;
		}
		
		row = sheet.createRow(rowIndex);
		for (int i = 0; i < columnNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(style);
		}
		rowIndex++;

		// 写入数据
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("皮料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != leatherData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + leatherData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < leatherData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(leatherData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("面料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != fabricData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + fabricData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < fabricData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(fabricData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("辅料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != supportData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + supportData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < supportData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(supportData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

	}

	private void exportClothMaterialSheet(Workbook workbook, String sheetTitle,
			Cloth cloth, List<ClothMaterialDetailData> leatherList,
			List<ClothMaterialDetailData> fabricList,
			List<ClothMaterialDetailData> supportList) {

		String[] columnNames = new String[] { "项目", "部位", "单位", "用量", "数量",
				"订购数量", "单价", "金额", "订购日期", "备注", "需要解决的问题", "交货期"};

		String[][] leatherData = getMaterialTableData(leatherList,
				columnNames.length);
		String[][] fabricData = getMaterialTableData(fabricList,
				columnNames.length);
		String[][] supportData = getMaterialTableData(supportList,
				columnNames.length);

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		CellStyle colorStyle = null;

		if (workbook instanceof HSSFWorkbook) {
			HSSFColor lightGray = setColor((HSSFWorkbook) workbook,
					(byte) 0xE0, (byte) 0xE0, (byte) 0xE0);
			colorStyle = workbook.createCellStyle();
			colorStyle.setFillForegroundColor(lightGray.getIndex());
			colorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}

		style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		sheet = workbook.createSheet(sheetTitle);

		int rowIndex = 0;
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("款号");
		cell = row.createCell(1);
		cell.setCellValue(cloth.getType());
		rowIndex++;

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("款名");
		cell = row.createCell(1);
		cell.setCellValue(cloth.getName());
		rowIndex++;

		row = sheet.createRow(rowIndex);
		for (int i = 0; i < columnNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(style);
		}
		rowIndex++;

		// 写入数据
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("皮料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != leatherData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + leatherData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < leatherData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(leatherData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("面料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != fabricData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + fabricData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < fabricData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(fabricData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("辅料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != supportData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + supportData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < supportData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(supportData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

	}

	private void exportPriceCountSheet(Workbook workbook, String sheetTitle,
			Cloth cloth, List<ClothMaterialDetailData> leatherList,
			List<ClothMaterialDetailData> fabricList,
			List<ClothMaterialDetailData> supportList) {
		
		String[] columnNames = new String[] { "项目", "部位", "单位", "用量", "数量",
				"单价", "金额", "订购日期", "备注" };

		String[][] leatherData = getPriceTableData(leatherList,
				columnNames.length);
		String[][] fabricData = getPriceTableData(fabricList,
				columnNames.length);
		String[][] supportData = getPriceTableData(supportList,
				columnNames.length);

		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		CellStyle style = null;
		CellStyle colorStyle = null;

		if (workbook instanceof HSSFWorkbook) {
			HSSFColor lightGray = setColor((HSSFWorkbook) workbook,
					(byte) 0xE0, (byte) 0xE0, (byte) 0xE0);
			colorStyle = workbook.createCellStyle();
			colorStyle.setFillForegroundColor(lightGray.getIndex());
			colorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		}

		style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		sheet = workbook.createSheet(sheetTitle);

		int rowIndex = 0;
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("款号");
		cell = row.createCell(1);
		cell.setCellValue(cloth.getType());
		rowIndex++;

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("款名");
		cell = row.createCell(1);
		cell.setCellValue(cloth.getName());
		rowIndex++;
		
		row = sheet.createRow(rowIndex);
		for (int i = 0; i < columnNames.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(style);
		}
		rowIndex++;

		// 写入数据
		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("皮料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != leatherData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + leatherData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < leatherData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(leatherData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("面料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != fabricData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + fabricData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < fabricData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(fabricData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

		row = sheet.createRow(rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("辅料小计");
		cell.setCellStyle(colorStyle);
		rowIndex++;
		if (null != supportData) {
			int startIndex = rowIndex;
			int endIndex = rowIndex + supportData.length;
			for (int i = startIndex; i < endIndex; i++, rowIndex++) {
				row = sheet.createRow(rowIndex);
				int dataRowIndex = i - startIndex;
				for (int j = 0; j < supportData[dataRowIndex].length; j++) {
					cell = row.createCell(j);
					cell.setCellValue(supportData[dataRowIndex][j]);
					cell.setCellStyle(style);
				}
			}
		} else {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue("暂无数据");
			rowIndex++;
		}

	}

	@SuppressWarnings("deprecation")
	private String[][] getMaterialTableData(
			List<ClothMaterialDetailData> dataList, int length) {
		if (null == dataList || dataList.isEmpty()) {
			return null;
		}

		String[][] tableData = new String[dataList.size()][length];

		int index = 0;
		for (ClothMaterialDetailData data : dataList) {
			tableData[index][0] = data.getMaterialName();
			tableData[index][1] = data.getPart();
			tableData[index][2] = data.getUnitName();
			tableData[index][3] = String.valueOf(data.getConsumption());
			tableData[index][4] = (null == data.getCount() ? "" : String
					.valueOf(data.getCount()));
			tableData[index][5] = (null == data.getOrderCount() ? "" : String
					.valueOf(data.getOrderCount()));
			tableData[index][6] = (null == data.getPrice() ? "" : String
					.valueOf(data.getPrice()));
			double totalPrice = 0.0;
			if (null != data.getPrice() && null != data.getCount()) {
				totalPrice = data.getConsumption() * data.getPrice()
						* data.getCount();
			}
			tableData[index][7] = String.valueOf(totalPrice);
			tableData[index][8] = null == data.getOrderDate() ? "" : data
					.getOrderDate().toLocaleString();
			tableData[index][9] = data.getRemark();
			index++;
		}
		return tableData;
	}
	
	@SuppressWarnings("deprecation")
	private String[][] getPriceTableData(
			List<ClothMaterialDetailData> dataList, int length) {
		if (null == dataList || dataList.isEmpty()) {
			return null;
		}

		String[][] tableData = new String[dataList.size()][length];

		int index = 0;
		for (ClothMaterialDetailData data : dataList) {
			tableData[index][0] = data.getMaterialName();
			tableData[index][1] = data.getPart();
			tableData[index][2] = data.getUnitName();
			tableData[index][3] = String.valueOf(data.getConsumption());
			tableData[index][4] = (null == data.getCount() ? "" : String
					.valueOf(data.getCount()));
			tableData[index][5] = (null == data.getPrice() ? "" : String
					.valueOf(data.getPrice()));
			double totalPrice = 0.0;
			if (null != data.getPrice() && null != data.getCount()) {
				totalPrice = data.getConsumption() * data.getPrice()
						* data.getCount();
			}
			tableData[index][6] = String.valueOf(totalPrice);
			tableData[index][7] = null == data.getOrderDate() ? "" : data.getOrderDate().toLocaleString();
			tableData[index][8] = data.getRemark();
			index++;
		}
		return tableData;
	}

	@SuppressWarnings("deprecation")
	private String[][] getCountTableData(
			List<ClothMaterialDetailData> dataList, int length) {
		if (null == dataList || dataList.isEmpty()) {
			return null;
		}

		String[][] tableData = new String[dataList.size()][length];

		int index = 0;
		for (ClothMaterialDetailData data : dataList) {
			tableData[index][0] = data.getMaterialName();
			tableData[index][1] = data.getPart();
			tableData[index][2] = data.getUnitName();
			tableData[index][3] = String.valueOf(data.getConsumption());
			tableData[index][4] = (null == data.getCount() ? "" : String
					.valueOf(data.getCount()));
			tableData[index][5] = (null == data.getPrice() ? "" : String
					.valueOf(data.getPrice()));
			double totalPrice = 0.0;
			if (null != data.getPrice() && null != data.getCount()) {
				totalPrice = data.getConsumption() * data.getPrice()
						* data.getCount();
			}
			tableData[index][6] = String.valueOf(totalPrice);
			tableData[index][7] = null == data.getOrderDate() ? "" : data.getOrderDate().toLocaleString();
			tableData[index][8] = data.getRemark();
			index++;
		}
		return tableData;
	}

	private void setResponseInfo(HttpServletResponse response, String fileName) {
		response.setContentType("application/msexcel; charset=UTF-8");
		response.setHeader("Content-disposition", "attachment; filename="
				+ fileName);
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	private HSSFColor setColor(HSSFWorkbook workbook, byte r, byte g, byte b) {
		HSSFPalette palette = workbook.getCustomPalette();
		HSSFColor hssfColor = null;
		try {
			hssfColor = palette.findColor(r, g, b);
			if (hssfColor == null) {
				palette.setColorAtIndex(HSSFColor.LAVENDER.index, r, g, b);
				hssfColor = palette.getColor(HSSFColor.LAVENDER.index);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return hssfColor;
	}

}
