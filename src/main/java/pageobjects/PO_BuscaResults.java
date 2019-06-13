package pageobjects;

import common.GenericMethods;
import common.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PO_BuscaResults extends BasePage {
	GenericMethods gem;

	public PO_BuscaResults(WebDriver driver) {
		super(driver);
		gem = new GenericMethods(driver);
	}
	
//	@FindBy(how = How.LINK_TEXT, using = linkText)
//	protected WebElement searchQueryTop;

	public void clickOnItem(String linkText) {
		driver.findElement(By.linkText(linkText)).click();
	}	
		
}
