package pageobjects;

import common.GenericMethods;
import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class PO_PaginaPrincipal extends BasePage {
	GenericMethods gem;

	public PO_PaginaPrincipal(WebDriver driver) {
		super(driver);
		gem = new GenericMethods(driver);
	}

	@FindBy(how = How.ID, using = "search_query_top")
	protected WebElement searchQueryTop;
	
	@FindBy(how = How.NAME, using = "submit_search")
	protected WebElement submitSearch;

	public void getHomePage(String url) {
		gem.getHomePage(url);
	}

	public void setSearchItem(String item) {
		searchQueryTop.sendKeys(item);
	}	
	
	public void clickOnSubmitSearch() {
		submitSearch.click();
	}	
		
}
