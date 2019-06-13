package pageobjects;

import common.GenericMethods;
import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PO_Autenticacao extends BasePage {
	GenericMethods gem;

	public PO_Autenticacao(WebDriver driver) {
		super(driver);
		gem = new GenericMethods(driver);
	}
	
	@FindBy(how = How.ID, using = "email_create")
	protected WebElement emailCreate;

	@FindBy(how = How.ID, using = "SubmitCreate")
	protected WebElement submitCreate;

	public void setEmailCreate(String email) {
		emailCreate.sendKeys(email);
	}	
	
	public void clickOnCreateAccount() {
		submitCreate.click();
	}	
			
}
