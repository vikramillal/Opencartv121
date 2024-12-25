package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage
{
    public HomePage(WebDriver driver)
    {
    	super(driver);
    }
    
    @FindBy(xpath="//span[normalize-space()='My Account']") WebElement lnk_MyAccount;
    @FindBy(xpath="//a[normalize-space()='Register']") WebElement lnk_Register;
    @FindBy(linkText="Login") WebElement lnk_Login;
    
    
    public void clickMyAccount()
    {
    	lnk_MyAccount.click();
    }
    
    public void clickRegister()
    {
    	lnk_Register.click();
    }
	
	public void clickLogin()
	{
		lnk_Login.click();
	}
	
	
	
}
