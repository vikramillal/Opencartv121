package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportsManager implements ITestListener
{  
	public ExtentSparkReporter sparkreporter;  // UI of the report
	public ExtentReports extent;    // populate common information
	public ExtentTest test;  // Responsible for test execution,Passed,fail,skip
	String repName;
	
	public void onStart(ITestContext testContext)
	{
		/*
		SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		Date dt=new Date();
		String currentdatetimestamp=df.format(dt);
		*/
		
		String timestamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time stamp
		repName="Test-Report-"+timestamp+".html";
		
		
		sparkreporter=new ExtentSparkReporter(".\\reports\\"+repName);  // location of the report
		sparkreporter.config().setDocumentTitle("Opencart Automation Report");  // title of the report
		sparkreporter.config().setReportName("Opencart Functional Testing");  // Name of the report
		sparkreporter.config().setTheme(Theme.DARK);   // theme
		
		extent=new ExtentReports();
		extent.attachReporter(sparkreporter);
		
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("SubModule","Customers");
		extent.setSystemInfo("UserName", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		
		String os=testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		
		String browser=testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		List<String> includedGroups =testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty())
		{
			extent.setSystemInfo("Groups",includedGroups.toString());
		}
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());                                                        
		test.log(Status.PASS,result.getName()+"got Succesfully Executed"); // update status
	}
	
	public void onTestFailure(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
		test.log(Status.FAIL,result.getName()+"Test Failed");
		test.log(Status.INFO,result.getThrowable().getMessage());
		
		try
		{
		String imgPath=new BaseClass().captureScreen(result.getName());
		test.addScreenCaptureFromPath(imgPath);
		}
		catch(IOException e1) {
		e1.printStackTrace();
		}
		
		
		
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test=extent.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP,result.getName()+"Test Skipped:");
		test.log(Status.INFO,result.getThrowable().getMessage());
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
		
		String pathOfExtentReport=System.getProperty("user.dir")+"\\reports\\"+repName;
		File extentReport=new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		 } catch(IOException e)
		{
			 e.printStackTrace();
		}
		/*
		try {
			URL url=new URL("file:///"+ System.getProperty("user.dir")+"\\reports\\"+repName);
			
			// create the email messsage
			ImageHtmlEmail email=new ImageHtmlEmail();
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googleemail.com");
			email.setSmtpPort(465);
			email.setAuthentication("illalvijay6@gmail.com", "vijay6illal");
			email.setSSLOnConnect(true);
			email.setFrom("illalvijay6@gmail.com");  // sender email
			email.setSubject("Test Results");
			email.setMsg("Please Find Attached Report..");
			email.addTo("illalvikram@gmail.com");    // receiver email
			email.attach(url, "extent report", "please check report");
			email.send();  // send the email
		   }
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		
	}

}
