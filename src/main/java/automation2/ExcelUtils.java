package automation2;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;

import org.apache.poi.xssf.usermodel.XSSFRow;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelUtils {

	public XSSFSheet excelWSheet;
	
	public XSSFWorkbook excelWBook;
	
	public XSSFCell cell;
	
	public XSSFRow row;
	
	public ExcelUtils(String workbookPath) {
		try {
			this.setExcelFile(workbookPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method
	//don't need the sheetname just yet
//	public static void setExcelFile(String Path,String SheetName) throws Exception {
	public void setExcelFile(String Path) throws Exception {
		System.out.println("setExcelFile");
		try {

			// Open the Excel file

		FileInputStream ExcelFile = new FileInputStream(Path);

		// Access the required test data sheet

		excelWBook = new XSSFWorkbook(ExcelFile);

		System.out.println("excelWBook: " + excelWBook.toString() );
//		ExcelWSheet = ExcelWBook.getSheet(SheetName);

		} catch (Exception e){

			throw (e);

		}

	}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

	public String getCellData(int RowNum, int ColNum) throws Exception{
	
			try{
	
				cell = excelWSheet.getRow(RowNum).getCell(ColNum);
	
				String CellData = cell.getStringCellValue();
	
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
	public int getRowCount(String sheet ) throws Exception{
		System.out.println("[getRowCount] excelWBook: " + excelWBook.toString());
		excelWSheet = excelWBook.getSheet(sheet);
		System.out.println("opening sheet: " + sheet + " in "+excelWSheet.toString());		
		int count=0;
		try {
			String cellData;
			do {
				cellData = this.getCellData(count,0);
				System.out.println("getting cell data: " + cellData + " ["+cellData.length()+"]");
//				if(cellData.equals("")) {
//					break;
//				}else {
					count++;
//				}
			}while(cellData.length()>0);
		}catch(Exception e) {
			System.out.println("getRowCount failed");
		}

		return count;
	}
	
	public String getColumnTop(int colNum, String sheetName) throws Exception{
		String tempStr = "";		
		try {
			excelWSheet = excelWBook.getSheet(sheetName);
			tempStr = this.getCellData(0, colNum);
		}catch(Exception e) {
			System.out.println("getColumnTop failed");
		}
		return tempStr;
	}
	
	public int getColumnCount(String sheet) throws Exception{
		int count=0;
		try {
			excelWSheet = excelWBook.getSheet(sheet);
			while(true) {
				String cellData = this.getCellData(0,count);
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
	
	public String[] getColumnData(int colNum, int count, String sheetName) throws Exception{
		System.out.println("getColumnData");
		String[] columnData = new String[count];
		excelWSheet = excelWBook.getSheet(sheetName);
		try {
			for(int i=0; i<count; i++) {
				columnData[i] = this.getCellData(i, colNum);
			}
			
		}catch(Exception e) {
			System.out.println("Couldn't getCellData()"); 
		}
		return columnData;				
		
	}
	
	//This method is to write in the Excel cell, Row num and Col num are the parameters
	
	public void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{
	
			try{
	
				row  = excelWSheet.getRow(RowNum);
	
				cell = row.getCell(ColNum, row.RETURN_BLANK_AS_NULL);
	
				if (cell == null) {
		
					cell = row.createCell(ColNum);
		
					cell.setCellValue(Result);
		
				} else {
	
					cell.setCellValue(Result);

				}
	
	// Constant variables Test Data path and Test Data file name
	
				FileOutputStream fileOut = new FileOutputStream(Constant.Path_TestData + Constant.File_TestData);

				excelWBook.write(fileOut);

				fileOut.flush();

				fileOut.close();
	
			}catch(Exception e){	
					throw (e);	
			}
	
	}

}

