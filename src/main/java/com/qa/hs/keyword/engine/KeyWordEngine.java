package com.qa.hs.keyword.engine;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.qa.hs.keyword.base.Base;
public class KeyWordEngine {
	public WebDriver driver;
	public Properties prop;
	public static Workbook book;
	public static Sheet sheet;
	public Base base;
	public WebElement ele;
	public final String SCENARIO_SHEET_PATH="C:\\Users\\ADMIN\\Desktop\\Selenium WebDriver\\KeywordDrivenHubspot\\src\\main\\java\\com\\qa\\hs\\keyword\\scenarios\\hubspot.xlsx";
	public void startExecution(String sheetName) throws IOException {
		String locatorName=null;
		String locatorValue=null;
		FileInputStream file=null;
		try {
		file=new FileInputStream(SCENARIO_SHEET_PATH);
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book=WorkbookFactory.create(file);
		} catch (EncryptedDocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//try {
		sheet=book.getSheet(sheetName);
		int k=0;
		for(int i=0;i<sheet.getLastRowNum();i++) {
			try {
			String locatorcolvalue=sheet.getRow(i+1).getCell(k+1).toString().trim();//id=username
			if(!locatorcolvalue.equalsIgnoreCase("NA")) {
				//locatorcolvalue.split("=")[0].trim();
				locatorName=locatorcolvalue.split("=")[0].trim();//id
				locatorValue=locatorcolvalue.split("=")[1].trim();//username
				
			}
			String action=sheet.getRow(i+1).getCell(k+2).toString().trim();
			String value=sheet.getRow(i+1).getCell(k+3).toString().trim();//last column
			switch(action) {
			case "open browser":
				base=new Base();
				prop=base.init_properties();
				//base.init_driver(browserName);
				if(value.isEmpty()||value.equals("NA")) {
					driver=base.init_driver(prop.getProperty("browser"));
				}else {
					driver=base.init_driver(value);
				}
				break;
			case "enter url":
				if(value.isEmpty()||value.equals("NA")) {
					driver.get(prop.getProperty("url"));
				}else {
					driver.get(value);
				}
				break;
			case "quit":
				driver.quit();
				break;
			
			default:
				break;
			}
		
			//if(locatorName!=null) {
			switch(locatorName) {
			case "id":
				ele=driver.findElement(By.id(locatorValue));
				if(action.equalsIgnoreCase("sendkeys")) {
					ele.clear();
					ele.sendKeys(value);
				}else if(action.equalsIgnoreCase("click")){
					ele.click();
				}
				//locatorName=null;
				break;
			case "linkText":
				ele=driver.findElement(By.linkText(locatorValue));
				ele.click();
				locatorName=null;
				break;
		
	}
		}
		catch(Exception e) {
			//e.printStackTrace();		
	}
		}
}
}
