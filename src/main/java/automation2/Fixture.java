package automation2;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Fixture {
	Constant constant;
//	ExcelUtils excelUtils;//static class
	AutomationToolset automationToolset;	
	Product attributesTemplate;
	
	public String configName;
	public void setConfigName (String temp) {
		configName = temp;
	}
	public String getConfigName() {
		return configName;
	}
	
	public String configSheetName;
	public void setConfigSheetName(String temp) {
		configSheetName = temp;
	}
	public String getConfigSheetName() {
		return configSheetName;
	}
	
	public Fixture(String workbookPath) {
		try {
			automationToolset = new AutomationToolset();
			automationToolset.willSimulateNavigation = "http://alpine:8080/test.html";
			automationToolset.navigateByGlobalAddress();
			ExcelUtils.setExcelFile(workbookPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Invalid excel sheet.");
		}		
//		productListing = generateProductListing();		
//		List<Product> generateProductListing(){
		
	}
	
	public void simulate() {
		String imageString="empty";
		String attributeCell="";
		String action="";
		String attributeIdPair="";
		String productCell="";
		Product product;
		
		//if configName and configSheetName is valid, then check attributesTemplate and product attributes
		if(configName.length()>0&&configSheetName.length()>0) {
			product = new Product(configSheetName, configName);		
			attributesTemplate = new Product(configSheetName, "customAttributeIdPair");	
			
			for(int i=0; i<attributesTemplate.attributeCount; i++) {
				attributeCell = attributesTemplate.attributes[i];
				action = attributeCell.split(":")[0];
				attributeIdPair = attributeCell.split(":")[1];
				productCell = product.attributes[i];
				
				if(action.equals("select")&&(productCell.length()>0)) {
					automationToolset.setCustomAttributeIdPair(attributeIdPair);
					automationToolset.setWillSimulateDropdownSelect(productCell);
					automationToolset.selectFromDropdown();
				} 
			}
		}
//		return imageString;
		return;
	}
	
	public static void main(String[] args) {
		Fixture fixture = new Fixture();
	}
}
