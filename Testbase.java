package com.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Testbase {

	public static  WebDriver  driver;
	public static  WebDriverWait wait;
	public static  File file;
	public static FileInputStream fs;
	public static  Properties  prop;	
	public static String 	browserName;
	public static  EventFiringWebDriver e_driver;
	
	
	public  Testbase() throws IOException
	{
		//  step 1 use File class  constructor to  store properties file path
		file= new File("C:\\eclipse workspace\\ProductConfigurator\\src\\main\\java\\com\\properties\\confiq.properties");
		
		//  Step  2   create constructor  of  FileInputStream   class to read data  from properties file
	   fs= new FileInputStream(file);
	   
		//  Step 3  create  constructor of Properties  class  to interact file type
		prop= new Properties();
		
		//  step 4 load data into current file
		prop.load(fs);	
	}
	
	
	 //step 2 initialization browser	
	 public static  void  initialization() throws IOException
	 {
	 //String browserName="fire fox";
	  browserName=prop.getProperty("browser");//fire fox
	 	if(browserName.equals("firefox"))
	 	{
	 		WebDriverManager.firefoxdriver().setup();
	 		driver= new FirefoxDriver();
	 	}else if(browserName.equals("chrome"))
	 	{
	 		WebDriverManager.chromedriver().setup();
	 		driver= new ChromeDriver();
	 	}else if(browserName.equals("ie"))
	 	{
	 		WebDriverManager.iedriver().setup();
	 		driver= new InternetExplorerDriver();
	 	}else
	 	{
	 		System.out.println(" selenium  tool dont support the browser & browser name is -->"+ browserName);
	 	}
	//  common steps
	//  web driver Fire Event ---> To generate  Selenium actions logs
		e_driver= new EventFiringWebDriver(driver);
	// now  create object  of 	EventFiringWebDriver   to register  it with EventfireongWebdriver
//		eventlistener= new WebEventListner();
//		e_driver.register(eventlistener);
//		driver=e_driver;
	 	//driver.get("https://classic.freecrm.com/login.cfm");
		driver.get(prop.getProperty("url"));//
		driver.manage().window().maximize();//  optional steps
		//driver.manage().deleteAllCookies();//  optional steps
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICITWAIT_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGELOAD_TIMEOUT, TimeUnit.SECONDS);
}
	 public String getScreenshotpath(String testCaseName,WebDriver driver) {
			
			TakesScreenshot scrShot =((TakesScreenshot)driver);
			File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
			String destinationFile = System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
			try {
				FileUtils.copyFile(SrcFile, new File(destinationFile ));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}
			return System.getProperty("user.dir")+"\\reports\\"+testCaseName+".png";
		}

}
