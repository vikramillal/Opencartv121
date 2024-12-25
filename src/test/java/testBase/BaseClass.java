package testBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;  // log4j
import org.apache.logging.log4j.Logger;      //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	
	public static  WebDriver driver;  // static because refer 8.4 step in framework doc(print)
	public Logger logger; 
	public Properties p;
	
	   @BeforeClass(groups= {"Sanity","Regression","Master"})
	   @Parameters({"os","browser"})
	   public void setup(String os,String br) throws IOException
	   {
		   // Loading config.properties file
		   FileReader file=new FileReader("./src//test//resources//config.properties");
		   p=new Properties();
		   p.load(file);
		    
		   logger=LogManager.getLogger(this.getClass());
		  
		   //This is for Remote Grid setup
		   if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		   {
			   
			   DesiredCapabilities capabilities=new DesiredCapabilities ();
			   
			   
			   //os
			   if(os.equalsIgnoreCase("Windows"))
			   {
				   capabilities.setPlatform(Platform.WIN10);
			   }
			   else if(os.equalsIgnoreCase("linux"))
			   {
				   capabilities.setPlatform(Platform.LINUX);
			   }
			   else if(os.equalsIgnoreCase("mac"))
			   {
				   capabilities.setPlatform(Platform.MAC);
			   }
			   else
			   {
				   System.out.println("no matching os");
				   return;
			   }
			   
			   //browser
			   switch(br.toLowerCase())
			   {
			   case "chrome": capabilities.setBrowserName("chrome"); break;
			   case "edge": capabilities.setBrowserName("Microsoftedge");break;
			   case "firefox": capabilities.setBrowserName("firefox"); break; 
			   default : System.out.println("No matching browser"); return;
			   }
			   
			   driver=new RemoteWebDriver(new URL(" http://192.168.29.36:4444/wd/hub"),capabilities);
			   
		   }
		   
		   //This is for local environment setup
		   
		   if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		   {
			   switch(br.toLowerCase())
			   {
			   case "chrome": driver=new ChromeDriver(); break;
			   case "edge":  driver=new EdgeDriver();break;
			   case "firefox": driver=new FirefoxDriver(); break;
			   default :System.out.println("Invalid Browser name...");return;
			   }
			   
			   
		   }
		   
		   
		  
		   driver.manage().deleteAllCookies();
		   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		   driver.get(p.getProperty("appURL1"));  // reading property file from config.properties
		   driver.manage().window().maximize();
	   }
	   
	   @AfterClass(groups= {"Sanity","Regression","Master"})
	   public void tearDown()
	   {
		   driver.quit();
	   }
	   
	   public String randomestring()
	   {
		 String generatedstring=RandomStringUtils.randomAlphabetic(5);
		 return generatedstring;
	   }
	   public String randomenumber()
	   {
		   String generatednumber=RandomStringUtils.randomNumeric(10);
		   return generatednumber;
	   }
	   
	   public String randomeAlphaNumberic()
	   {
		 String generatedstring=RandomStringUtils.randomAlphabetic(3);
		 String generatednumber=RandomStringUtils.randomNumeric(3);
		 return (generatedstring+generatednumber);
	   }
	   
	   public String captureScreen(String tname) throws IOException
	   {
		   String timestamp=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		   TakesScreenshot takesscreenshot=(TakesScreenshot) driver;
		   File sourceFile=takesscreenshot.getScreenshotAs(OutputType.FILE);
		   
		   String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname + "_"+timestamp+".png";
		   File targetfile=new File(targetFilePath);
		   sourceFile.renameTo(targetfile);
		   return targetFilePath;
	   }
	
	

}
