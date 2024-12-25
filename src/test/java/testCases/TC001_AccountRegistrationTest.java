package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{
   
   @Test(groups={"Regression","Master"})
   public void test_Account_Registration()
   {
	   try
	   {
	   logger.info("*****Starting TC001_AccountRegistrationTest*****");
	   HomePage hp=new HomePage(driver);
	   hp.clickMyAccount();
	   logger.info("Clicked on my account link");
	   hp.clickRegister();
	   logger.info("Clicked on Register link");
	   
	   AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
	   
	   logger.info("Providing Customer details");
	   regpage.setFirstName(randomestring().toUpperCase());
	   regpage.setLastName(randomestring().toLowerCase());
	   regpage.setEmail(randomestring()+"@gmail.com");
	   regpage.setTelephone(randomenumber());
	   
	   String password= randomeAlphaNumberic();
	  
	   regpage.setPassword(password);
	   regpage.setConfirmPassword(password);
	   regpage.setPrivacyPolicy();
	   regpage.clickContinue();
	   logger.info("Vallidating Expected message");
	   String confmsg=regpage.getConfirmationMsg();
	   if(confmsg.equals("Your Account Has Been Created!"))
	   {
		   Assert.assertTrue(true);
	   }
	   else
	   {
		   logger.error("Test Failed");
		   logger.debug("Debug logs..");
		   Assert.assertTrue(false);
	   }
	   
	   //Assert.assertEquals(confmsg, "Your Account Has Been Created");
	   }
	   catch(Exception e)
	   {
		   
		   Assert.fail();
	   }
	   
	   
	   logger.info("*****Finished TC001_AccountRegistrationTest*****");
	   
	   
   }
   
  
   
   
   
   
   
	
	
	
	
	
	
	
	
	
}
