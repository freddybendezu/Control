package com.smartbrands.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

public class Excel2SQLiteHelper {

	public static void insertExcelToSqlite1(DBAdapter dbAdapter, Sheet sheet) {
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			Row row = rit.next();
			ContentValues values = new ContentValues();
			values.put(DBAdapter.ALM_ID_ALMACEN, row.getCell(0).getStringCellValue());
			values.put(DBAdapter.ALM_NOM_ALMACEN, row.getCell(1).getStringCellValue());
			values.put(DBAdapter.ALM_ID_SUCURSAL, row.getCell(2).getStringCellValue());
			if (dbAdapter.insert(DBAdapter.ALM_TABLE, values) < 0) {
				Log.e("Error", "Error en adaptador");
				return; 
			}
		}
	}

	public static void insertExcelToSqlite2(DBAdapter dbAdapter, Sheet sheet) {
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			Row row = rit.next();
			ContentValues values = new ContentValues();
			values.put(DBAdapter.UBI_ID_UBICACION, row.getCell(0).getStringCellValue());
			values.put(DBAdapter.UBI_NOM_UBICACION, row.getCell(1).getStringCellValue());
			if (dbAdapter.insert(DBAdapter.UBI_TABLE, values) < 0) {
				Log.e("Error", "Error en adaptador");
				return; 
			}
		}
	}

	
	public static void insertExcelToSqlite3(DBAdapter dbAdapter, Sheet sheet) {
		for (Iterator<Row> rit = sheet.rowIterator(); rit.hasNext();) {
			Row row = rit.next();
			ContentValues values = new ContentValues();
			values.put(DBAdapter.PRO_ID_PRODUCTO, row.getCell(0).getStringCellValue());
			values.put(DBAdapter.PRO_NOM_PRODUCTO, row.getCell(1).getStringCellValue());
			if (dbAdapter.insert(DBAdapter.PRO_TABLE, values) < 0) {
				Log.e("Error", "Error en adaptador");
				return; 
			}
		}
	}


	public static void createExcel(Context context) {
		Workbook workbook = new HSSFWorkbook();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);//
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_BOTTOM);
		// 
		Object[] values0 = { "TangYC", 70, 20, new Date() };
		Object[] values1 = { "Eric", 71, 21, new Date() };
		Object[] values2 = { "Tomas", 72, 22, new Date() };
		Object[] values3 = { "Tonny", 73, 23, new Date() };
		Object[] values4 = { "Jimmy", 74, 24, new Date() };
		//
		Sheet sheet1 = workbook.createSheet("class1");
		insertRow(sheet1, 0, values0, cellStyle);
		insertRow(sheet1, 1, values1, cellStyle);
		insertRow(sheet1, 2, values2, cellStyle);
		insertRow(sheet1, 3, values3, cellStyle);
		insertRow(sheet1, 4, values4, cellStyle);
		// 
		Object[] values5 = { "Aron", 81, 25, Calendar.getInstance() };
		Object[] values6 = { "Truman", 82, 26, Calendar.getInstance() };
		Object[] values7 = { "T-bag", 83, 27, Calendar.getInstance() };
		Object[] values8 = { "WhyMe", 84, 28, Calendar.getInstance() };
		Object[] values9 = { "Youknowit", 85, 29, Calendar.getInstance() };
		Sheet sheet2 = workbook.createSheet("class2");

		insertRow(sheet2, 0, values5, cellStyle);
		insertRow(sheet2, 1, values6, cellStyle);
		insertRow(sheet2, 2, values7, cellStyle);
		insertRow(sheet2, 3, values8, cellStyle);
		insertRow(sheet2, 4, values9, cellStyle);

		//
		File file = new File("/sdcard/demo.xls");
		FileOutputStream fos = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			workbook.write(fos);
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void insertRow(Sheet sheet, int rowIndex,
			Object[] columnValues, CellStyle cellStyle) {
		Row row = sheet.createRow(rowIndex);
		int column = columnValues.length;
		for (int i = 0; i < column; i++) {
			createCell(row, i, columnValues[i], cellStyle);
		}
	}


	public static void createCell(Row row, int columnIndex, Object cellValue,
			CellStyle cellStyle) {
		Cell cell = row.createCell(columnIndex);
		// 
		if (cellValue instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String value = format.format((Date) cellValue);
			cell.setCellValue(value);
		} else if (cellValue instanceof Calendar) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String value = format.format(((Calendar) cellValue).getTime());
			cell.setCellValue(value);
		} else {
			cell.setCellValue(cellValue.toString());
		}
		cell.setCellStyle(cellStyle);
	}

}
