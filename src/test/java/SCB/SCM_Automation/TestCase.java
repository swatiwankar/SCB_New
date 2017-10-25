package SCB.SCM_Automation;

import java.io.IOException;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.codoid.products.exception.FilloException;
import Config.ConfigClass;
import DataProvider.DataProvider;
import Utilities_Helper.GetScreenShot;
import Utilities_Helper.HelperClass;
import Pages.Registation;
import TestReporter.TestReporterClass;

public class TestCase 
{;
	ExtentReports extent;
	ExtentTest test;
	WebDriver driver;
	HelperClass helperClass;
	Properties prop;
	
	
	DataProvider GetExcelData = new DataProvider();
    HashMap<String, String> excelHashMapValues = new HashMap <String, String>();
	
	@BeforeClass
	public void initialize() throws Exception
	{
	 extent = TestReporterClass.GetExtent();
	 extent.setSystemInfo("Operating System", System.getProperty("os.name"));
   	 extent.setSystemInfo("Java version", System.getProperty("java.runtime.version"));
   	 extent.setSystemInfo("user name", System.getProperty("user.name"));
	 
//   	ConfigClass config = new ConfigClass();
//    driver =config.BrowserSetUp();
	}
	
	@BeforeMethod
	public void precondition(Method method) throws Exception
	{
		test = extent.createTest(method.getName()).assignCategory("UAT").assignAuthor(System.getProperty("user.name"));
		ConfigClass config = new ConfigClass();
	    driver =config.BrowserSetUp();
	}
	
	@Test
	public void Test_case_1() throws FilloException, IOException
	{
		String TestCaseID = "TC_002";
		GetExcelData.extractExcelData(TestCaseID, excelHashMapValues);
		
		helperClass = new HelperClass(driver);
		Registation d = new Registation(driver,helperClass);
		
		d.doRegistation(excelHashMapValues.get("fname"),excelHashMapValues.get("lname"),excelHashMapValues.get("n_country"),excelHashMapValues.get("month1"),excelHashMapValues.get("date1"),excelHashMapValues.get("year1"),excelHashMapValues.get("phn_no"),excelHashMapValues.get("username1"),excelHashMapValues.get("email1"),excelHashMapValues.get("pwd"),excelHashMapValues.get("cfm_pwd"));
		test.log(Status.INFO, "Step 1");
		WebElement text_messge =driver.findElement(By.xpath("//*[@id='post-49']/div/p"));
		test.log(Status.INFO, "Step -2");
		Assert.assertEquals(text_messge.getText(),"Thank you for your registration");
		test.log(Status.INFO, "Step -3");
	}
//	@Test
//	public void Test_case_2() throws FilloException, IOException
//	{
//		String TestCaseID = "TC_002";
//		GetExcelData.extractExcelData(TestCaseID, excelHashMapValues);
//		
//		helperClass = new HelperClass(driver);
//		Registation d = new Registation(driver,helperClass);
//		
//		d.doRegistation(excelHashMapValues.get("fname"),excelHashMapValues.get("lname"),excelHashMapValues.get("n_country"),excelHashMapValues.get("month1"),excelHashMapValues.get("date1"),excelHashMapValues.get("year1"),excelHashMapValues.get("phn_no"),excelHashMapValues.get("username1"),excelHashMapValues.get("email1"),excelHashMapValues.get("pwd"),excelHashMapValues.get("cfm_pwd"));
//		test.log(Status.INFO, "Step 1");
//		WebElement text_messge =driver.findElement(By.xpath("//*[@id='post-49']/div/p"));
//		test.log(Status.INFO, "Step -2");
//		Assert.assertEquals(text_messge.getText(),"Thank you for your registration");
//		test.log(Status.INFO, "Step -3");
//	}
	
//	@Test
//	public void testcase_1() throws Exception
//	{
//		String TestCaseID = "TC_003";
//		GetExcelData.extractExcelData(TestCaseID, excelHashMapValues);
//		helperClass = new HelperClass(driver);
//	
//	    Login lg = new Login (driver, helperClass);
//		lg.login(excelHashMapValues.get("Uname"),excelHashMapValues.get("password"));
//		
//		Product product = new Product(driver, helperClass);
//		product.addtocart(excelHashMapValues.get("qunty"));	
//	}
	@Test
	public void testCase_2()
	{
		throw new SkipException("This test case is skipped");
		//Assert.assertTrue(true);
   }
	@Test
	public void testCase_3()
	{
		Assert.assertTrue(true);
	}
	
	@AfterMethod
	public void Reporter(ITestResult result) throws IOException{
		
		//Result Reporting to Extent Logic
//		String TestName = result.getMethod().getConstructorOrMethod().getName();
//
//		
//		System.out.println("This is my test name:" + TestName);
		if(result.getStatus()==ITestResult.FAILURE)
		{	
			System.out.println("entered after failed");
			//extent.createTest(testName:"test1",description:"Test FAILED");
            String screenShotPath = GetScreenShot.capture(driver, "screenShotName");

            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" FAILED due to below issues", ExtentColor.RED));
			//here MarkupHelper.createLabel is ue to get the name of test case and to add the color 
           
            // ****** Line is updated
            //test.fail(result.getThrowable().toString());
            test.fail(result.getThrowable());
           // Throwable t = result.getThrowable();
		//	test.fail(ExceptionUtils.getStackTrace(t));
			
		test.log(Status.FAIL, "Snapshot below: ").addScreenCaptureFromPath(screenShotPath);
        //   test.log(Status.INFO, "Screen shot",  MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build());

		}
		else if(result.getStatus()==ITestResult.SUCCESS)
		{
			System.out.println("entered after success");
			//extent.createTest("test2","Test PASSED");
			 String screenShotPath = GetScreenShot.capture(driver, "screenShotName");
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"Test Case PASSED", ExtentColor.GREEN));
			test.pass("Snapshot below: " + test.addScreenCaptureFromPath(screenShotPath));
		}
		else
		{
			System.out.println("entered after skipp");
			//extent.createTest("test3","Test SKIPPED");
			test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+"Test Case SKIPPED", ExtentColor.YELLOW));
			test.skip(result.getThrowable());
		}
		driver.close();
	}
	
	@AfterClass
	public void tearDown() throws Exception
	{
		extent.flush();
		
		
	}
}
