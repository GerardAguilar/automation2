package automation2;

public class Product{
	public int attributeCount;
	public String[] attributes;
	public String[][] optionsList;
//	String[] markers;
	String type;
	String sheetName;
	ExcelUtils excelUtils;
	
	
	public Product(String sheet, String productName, ExcelUtils excelUtilsParam) {
		sheetName = sheet;
		excelUtils = excelUtilsParam;
		attributeCount = countAttributes();
		extractAttributesFromColumn(productName);		
	}
	
	public String getAttribute(int i) {
		System.out.println("getAttribute: " + i);
		String temp = attributes[i];
		System.out.println("attributes[i]: " + temp);
		return temp;
	}

	public void extractAttributesFromColumn(String productName) {
		System.out.println("extractAttributesFromColumn start");
		int productCount=0;
		try {
			productCount = excelUtils.getColumnCount(sheetName);
		} catch (Exception e) {
			System.out.println("getColumnCount failed");
			e.printStackTrace();
		}
		int column = -1;
		String str="";
		
		//first find the column we care about by using productName
		for(int i=0; i<productCount; i++) {
			try {
				str = excelUtils.getColumnTop(i, sheetName);
			} catch (Exception e) {
				e.printStackTrace();
			}//I don't like passing sheetName so many times
			if(str.equals(productName)) {
				column = i;
				break;
			}
		}		
		if(column>=0) {
			try {
				attributes = excelUtils.getColumnData(column, attributeCount, sheetName);
//				markers = excelUtils.getColumnData(column, attributeCount, sheetName);
			} catch (Exception e) {
				System.out.println("getColumnData failed");
				e.printStackTrace();
			}
		}				
		System.out.println("extractAttributesFromColumn end");
		return;
	}
	
//	private void assignAttributesFromMarkers() {
//		
//	}
//	
//	private void generateOptions() {
//		
//	}

	private int countAttributes() {
		int count=0;
		System.out.println("excelUtils: " + excelUtils.toString());
		System.out.println("sheetName: " + sheetName);
		try {
			count = excelUtils.getRowCount(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}