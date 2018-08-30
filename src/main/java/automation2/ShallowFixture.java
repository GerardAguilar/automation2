package automation2;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ShallowFixture {
	Constant constant;
	ExcelUtils excelUtils;
	AutomationToolset automationToolset;	
	Product attributesTemplate;
	String workbookPath;
	
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
	
	public ShallowFixture() {
		try {
			workbookPath = "C:\\Users\\gaguilar\\Desktop\\AndersenTestcases.xlsx";
			automationToolset = new AutomationToolset();
//			automationToolset.willSimulateNavigation = "http://inhancemetrics.com:3012/test-new.html";
//			automationToolset.willSimulateNavigation = "http://alpine:8080/test.html";
			automationToolset.willSimulateNavigation = "https://andersen.inhance.io/app/test.html";
			automationToolset.navigateByGlobalAddress();
			excelUtils = new ExcelUtils(workbookPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Invalid excel sheet.");
		}				
	}
	
	public String simulate() {
		System.out.println("simulate()");
		String imageString="empty";
		String attributeCell="";
		String action="";
		String attributeIdPair="";
		String productCell="";
		Product product;
		
		//if configName and configSheetName is valid, then check attributesTemplate and product attributes
		if(configName.length()>0&&configSheetName.length()>0) {
			System.out.println("configName: "+ configName+" length is good. configSheetName: " +configSheetName+" length is good.");
			System.out.println("excelUtils: "+excelUtils.toString());
			//attributesTemplate is our set of web elements to target, really only needs to be done once per sheet, but for now we'll do it at every simulate() call
			attributesTemplate = new Product(configSheetName, "customAttributeIdPair", excelUtils);	
			product = new Product(configSheetName, configName, excelUtils);		
			
			System.out.println("attributesTemplate.attributeCount: " + attributesTemplate.attributeCount);
			System.out.println("attributesTemplate: " + attributesTemplate.toString());
			
			for(int i=0; i<attributesTemplate.attributeCount; i++) {
							
				attributeCell = attributesTemplate.getAttribute(i);
				if(!attributeCell.contains(":")) {
					//do nothing
				}else {
					action = attributeCell.split(":")[0];
					attributeIdPair = attributeCell.split(":")[1];
					productCell = product.attributes[i];
					
					System.out.println(	"\nattributeCell: " + attributeCell + "\n" +
										"action: " + action + "\n" +
										"attributeIdPair: " + attributeIdPair + "\n" +
										"productCell: " + productCell);
					
					if(action.equals("select")&&(productCell.length()>0)) {
						automationToolset.setCustomAttributeIdPair(attributeIdPair);
						automationToolset.setWillSimulateDropdownSelect(productCell);
						automationToolset.waitFor();
						automationToolset.selectFromDropdown();
					}else if(action.equals("click")&&(productCell.length()>0)) {
						automationToolset.setCustomAttributeIdPair(attributeIdPair);
						automationToolset.setWillSimulateClick(true);
						automationToolset.waitFor();
						automationToolset.clickElement();
					}else if(action.equals("mouse")) {
						if(attributeIdPair.equals("hold")) {
//							automationToolset.setWillDragMouseByOffset(productCell);
							automationToolset.mouseHold();
							automationToolset.takeScreenshot(configSheetName, configName);
							automationToolset.appendImageString(configSheetName, configName);	
						}else if(attributeIdPair.equals("teleport")&&(productCell.length()>0)) {
							automationToolset.setWillMoveMouseByOffset(productCell);
							automationToolset.mouseMove();
							automationToolset.takeScreenshot(configSheetName, configName);
							automationToolset.appendImageString(configSheetName, configName);	
						}else if(attributeIdPair.equals("release")) {
//							automationToolset.setWillMoveMouseToXpath(productCell);
							automationToolset.mouseRelease();
							automationToolset.takeScreenshot(configSheetName, configName);
							automationToolset.appendImageString(configSheetName, configName);	
						}else if(attributeIdPair.equals("drag")) {
							automationToolset.setWillDragMouseByOffset(productCell);
							automationToolset.dragMouse();
							automationToolset.takeScreenshot(configSheetName, configName);
							automationToolset.appendImageString(configSheetName, configName);	
						}

					}
				}
			}
		}
		return automationToolset.postSimulation();
	}
	
//	public static void main(String[] args) {
//		ShallowFixture fixture = new ShallowFixture();
//	}
}
