package automation2;

public class Product{
	public int attributeCount;
	public String[] attributes;
	String type;
	String sheetName;
	
	
	public Product(String sheet, String productName) {
		sheetName = sheet;
		attributeCount = countAttributes();
		extractAttributesFromColumn(productName);
	}

	public void extractAttributesFromColumn(String productName) {
		int productCount=0;
		try {
			productCount = ExcelUtils.getColumnCount(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int column = -1;
		String str="";
		for(int i=0; i<productCount; i++) {
			try {
				str = ExcelUtils.getColumnTop(i, sheetName);
			} catch (Exception e) {
				e.printStackTrace();
			}//I don't like passing sheetName so many times
			if(str.equals(productName)) {
				column = i;
				break;
			}
		}		
		if(column>0) {
			try {
				attributes = ExcelUtils.getColumnData(column, attributeCount, sheetName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return;
	}

	private int countAttributes() {
		int count=0;
		try {
			count = ExcelUtils.getRowCount(sheetName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}