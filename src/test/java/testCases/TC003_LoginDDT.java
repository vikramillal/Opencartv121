package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

/*
 Data is Valid--Login Success--Test Pass---Logout
 Data is Valid--Login Failed---Test failed
 
 Data is InValid--Login Success--Test Failed--Logout
 Data is InValid--Login failed---Test Failed
 
 */

public class TC003_LoginDDT extends BaseClass{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven") // getting data from different class and package
	public void verify_LoginDDT(String email,String pwd,String exp) 
	{
		        logger.info("*** Starting TC003_LoginDDT *****");
		        
		        try
		        {
		        //HomePage
				HomePage hp=new HomePage(driver);
				hp.clickMyAccount();
				hp.clickLogin();
				
				
				//Login Page
				LoginPage lp=new LoginPage(driver);
				lp.setEmail(p.getProperty("email"));
				lp.setPassword(p.getProperty("password"));
				lp.clickLogin();
				
				//My Account Page
				
				 MyAccountPage macc=new MyAccountPage(driver);
				 boolean targetPage=macc.isMyAccountPageExists();
				 
				 /*
				 Data is Valid--Login Success--Test Pass---Logout
				 Data is Valid--Login Failed---Test failed
				 
				 Data is InValid--Login Success--Test Failed--Logout
				 Data is InValid--Login failed---Test Failed
				 
				 */
				 
				 if(exp.equalsIgnoreCase("Valid"))  // if data is valid
				  {
					 if(targetPage==true)    // login success
					  {
						 macc.clickLogout();        //logout
						 Assert.assertTrue(true);       // test pass
						          
					  }
					 else
					  {
						 Assert.assertTrue(false);
					  }
				  }
				 
				   if(exp.equalsIgnoreCase("Invalid"))    // if data is invalid
				    {
					  if(targetPage==true)           // login success
					  {
						 macc.clickLogout();    // logout
						 Assert.assertTrue(false);     // test failed
					  }
					  else
					  {
						 Assert.assertTrue(true);         // test passed
					  }
				   } 
				 
		        }
		        catch(Exception e)
		        {
		        	Assert.fail();
		        }
				 logger.info("*** Finished TC003_LoginDDT *****");
				
	}

}
