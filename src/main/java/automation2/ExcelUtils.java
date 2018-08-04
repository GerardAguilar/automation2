package automation2;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	
	private static XSSFWorkbook ExcelWBook;
	
	private static XSSFCell Cell;
	
	private static XSSFRow Row;

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	//don't need the sheetname just yet
//	public static void setExcelFile(String Path,String SheetName) throws Exception {
	public static void setExcelFile(String Path) throws Exception {

		try {

			// Open the Excel file

		FileInputStream ExcelFile = new FileInputStream(Path);

		// Access the required test data sheet

		ExcelWBook = new XSSFWorkbook(ExcelFile);

//		ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){

			throw (e);

		}

	}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public static String getCellData(int RowNum, int ColNum) throws Exception{
	
			try{
	
				Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
	
				String CellData = Cell.getStringCellValue();
	
				return CellData;
	
			}catch (Exception e){
	
				return"";
	
			}
	
	}
	
	/***
	 * Run through the first column and count attributes (no need to adjust start point at 0 since those are the headers anyway)
	 * @return
	 * @throws Exception
	 */
	public static int getRowCount(String sheet ) throws Exception{
		int count=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			while(true) {
				String cellData = ExcelUtils.getCellData(count,0);
				if(cellData.equals("")) {
					break;
				}else {
					count++;
				}
			}
		}catch(Exception e) {
			System.out.println("getRowCount failed");
		}

		return count;
	}
	
	public static String getColumnTop(int colNum, String sheetName) throws Exception{
		String tempStr = "";		
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			tempStr = ExcelUtils.getCellData(0, colNum);
		}catch(Exception e) {
			System.out.println("getColumnTop failed");
		}
		return tempStr;
	}
	
	public static int getColumnCount(String sheet) throws Exception{
		int count=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sheet);
			while(true) {
				String cellData = ExcelUtils.getCellData(0,count);
				if(cellData.equals("")) {
					break;
				}else {
					count++;
				}
			}
		}catch(Exception e) {
			System.out.println("getColumnCount failed");
		}

		return count;
	}
	
	public static String[] getColumnData(int colNum, int count, String sheetName) throws Exception{
		String[] columnData = new String[count];
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		try {
			for(int i=0; i<count; i++) {
				columnData[i] = ExcelUtils.getCellData(i, colNum);
			}
			
		}catch(Exception e) {
			System.out.println("Couldn't getCellData()"); 
		}
		return columnData;				
		
	}
	
	//This method is to write in the Excel cell, Row num and Col num are the parameters
	
	public static void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{
	
			try{
	
				Row  = ExcelWSheet.getRow(RowNum);
	
				Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
	
				if (Cell == null) {
		
					Cell = Row.createCell(ColNum);
		
					Cell.setCellValue(Result);
		
				} else {
	
					Cell.setCellValue(Result);

				}
	
	// Constant variables Test Data path and Test Data file name
	
				FileOutputStream fileOut = new FileOutputStream(Constant.Path_TestData + Constant.File_TestData);

				ExcelWBook.write(fileOut);

				fileOut.flush();

				fileOut.close();
	
			}catch(Exception e){	
					throw (e);	
			}
	
	}

}

