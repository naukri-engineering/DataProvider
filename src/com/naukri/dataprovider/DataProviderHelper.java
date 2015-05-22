package com.naukri.dataprovider;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.ServiceTestCase;

/**
 * 
 * @author gaurav.kumar
 *
 */
public class DataProviderHelper {

	private AndroidTestCase androidTestCase;

	public DataProviderHelper(AndroidTestCase androidTestCase) {
		this.androidTestCase = androidTestCase;
	}

	private static List<TestCaseIO> findRow(HSSFSheet sheet,
			String cellContent, Class<?> outputType, Class<?>... inputTypes) {

		List<TestCaseIO> rows = new ArrayList<TestCaseIO>();
		for (Row row : sheet) {
			boolean foundFirstValue = false;
			if (row.getCell(0) != null
					&& row.getCell(0).getRichStringCellValue().getString()
							.trim().equals(cellContent)) {
				List<Object> inputs = new ArrayList<Object>(inputTypes.length);
				int index = 0;
				for (Class<?> inputType : inputTypes) {
					Object input = getCellValue(row, index + 1, inputType);
					inputs.add(input);
					index = index + 1;
				}
				Object output = getCellValue(row, index + 1, outputType);
				TestCaseIO caseIO = new TestCaseIO();
				caseIO.input = inputs;
				caseIO.output = output;
				rows.add(caseIO);
				foundFirstValue = true;
			} else if (foundFirstValue) {
				break;
			}
		}
		return rows;
	}

	private static Object getCellValue(Row row, int col, Class<?> type) {
		Cell cell = row.getCell(col);
		if (cell != null) {
			if (type == String.class) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cell.getStringCellValue();
				if (value.equals("Null")) {
					return null;
				}
				return value;
			} else if (type == Integer.class || type == int.class) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return Integer.valueOf(cell.getStringCellValue());
			} else if (type == Long.class || type == long.class) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return Long.valueOf(cell.getStringCellValue());
			} else if (type == Float.class || type == float.class) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return Float.valueOf(cell.getStringCellValue());
			} else if (type == double.class || type == Double.class) {
				cell.setCellType(Cell.CELL_TYPE_NUMERIC);
				return cell.getNumericCellValue();
			} else if (type == short.class || type == Short.class) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return Short.valueOf(cell.getStringCellValue());
			} else if (type == Byte.class || type == byte.class) {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				return Byte.valueOf(cell.getStringCellValue());
			} else if (type == Boolean.class) {
				cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
				return cell.getBooleanCellValue();
			}

		}
		return "";
	}

	private Context getTestContext() {
		try {
			Method getTestContext = ServiceTestCase.class
					.getMethod("getTestContext");
			return (Context) getTestContext.invoke(androidTestCase);
		} catch (final Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}

	public List<TestCaseIO> getTestCases(String label, String excelFileName,
			Class<?> outputType, Class<?>... inputTypes){
		try {
			InputStream inputStream = getTestContext().getAssets().open(
					excelFileName);
			POIFSFileSystem fs = new POIFSFileSystem(inputStream);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			List<TestCaseIO> rows = findRow(sheet, label, outputType,
					inputTypes);
			wb.close();
			/*if (rows == null || rows.size() == 0) {
				throw new Exception("Rows not found in excel");
			}*/

			return rows;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class TestCaseIO {
		public List<Object> input;
		public Object output;
	}

}
