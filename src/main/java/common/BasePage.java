package common;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
	protected static WebDriver driver;
	protected WebDriverWait wait;

	public BasePage(WebDriver driver) {
		BasePage.driver = driver;
		wait = new WebDriverWait(driver, 10);
		PageFactory.initElements(driver, this);
	}
}
