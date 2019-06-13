package Runs;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import common.GenericMethods;
import common.Log;
import pageobjects.PO_Endereco;
import pageobjects.PO_CartSummary;
import pageobjects.PO_PaginaPrincipal;
import pageobjects.PO_Produto;
import pageobjects.PO_TipoPagamento;
import pageobjects.PO_BuscaResults;
import pageobjects.PO_Shipping;
import pageobjects.PO_Autenticacao;
import pageobjects.PO_InfoPessoal;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class RunJUnit {
	
	static WebDriver driver;
	static PO_PaginaPrincipal pg_POPaginaPrincipal;
	static PO_BuscaResults pg_search_result;
	static PO_Produto pg_POProduto;
	static PO_CartSummary pg_cart_summary;
	static PO_Autenticacao pg_singin_aut;
	static PO_InfoPessoal pg_singin_per_inf;
	static PO_Endereco pg_POEndereco;
	static PO_Shipping pg_PO_shipping;
	static PO_TipoPagamento pg_POTipoPagemnto;
	static Log log;
	static GenericMethods gem;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("*******************************");
		System.out.println("*          Inicio Teste       *");
		System.out.println("*******************************");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("*******************************");
		System.out.println("*          Fim Teste          *");
		System.out.println("*******************************");
	}

	@Before
	public void setUp() throws Exception {
		gem = new GenericMethods();
		driver = gem.getDriver();
		
		pg_POPaginaPrincipal = new PO_PaginaPrincipal(driver);
		pg_search_result = new PO_BuscaResults(driver);
		pg_POProduto = new PO_Produto(driver);
		pg_cart_summary = new PO_CartSummary(driver);
		pg_singin_aut = new PO_Autenticacao(driver);
		pg_singin_per_inf = new PO_InfoPessoal(driver);
		pg_POEndereco = new PO_Endereco(driver);
		pg_PO_shipping = new PO_Shipping(driver);
		pg_POTipoPagemnto = new PO_TipoPagamento(driver);
		
		log = new Log(driver);
	}

	@After
	public void tearDown() throws Exception {
		driver.close();
	}
	
	@Test
	public void test() {
		String url = "http://automationpractice.com/index.php";
		String item = "Faded Short Sleeve T-shirts";
		String quantity_wanted = "2";
		String size = "M";
		String color = "Blue";
		double price;
	
		String email = "test@automation.com";
		String firstname = "Pericles";
		String lastname = "Feltrin";
		String password = "123456";
		String address = "577 Flatbush Ave, Brooklyn";
		String city = "New York";
		String state = "New York";
		String postcode = "11226";
		String country = "United States";
		String number = "+5555999999999";
		String alias = "Ref. Addres";
		double priceShipping;
		
		pg_POPaginaPrincipal.getHomePage(url);
		log.screenshot();
		
		pg_POPaginaPrincipal.setSearchItem(item);
		log.screenshot();
		pg_POPaginaPrincipal.clickOnSubmitSearch();

		log.screenshot();
		pg_search_result.clickOnItem(item);
		
		price = pg_POProduto.getPrice()*Double.parseDouble(quantity_wanted);
		
		pg_POProduto.setQuantityWanted(quantity_wanted);
		pg_POProduto.setSize(size);
		pg_POProduto.setColor(color);
		log.screenshot();
		pg_POProduto.clickOnAddToCart();
		log.screenshot();
		pg_POProduto.clickOnProceedToCheckout();

		log.screenshot();
		assertEquals(price, pg_cart_summary.getTotalProducts(), 0);
		pg_cart_summary.clickOnProceedToCheckout();
		
		pg_singin_aut.setEmailCreate(email);
		log.screenshot();
		pg_singin_aut.clickOnCreateAccount();
		
		pg_singin_per_inf.setCompleteForm(firstname, lastname, password, address, city, state, postcode, country, number, alias);
		log.screenshot();
		pg_singin_per_inf.clickOnSubmitAccount();
		
		log.screenshot();
		Assert.assertEquals(address, pg_POEndereco.getDeliveryAddress());
		Assert.assertEquals(city+", "+state+" "+postcode, pg_POEndereco.getDeliveryCityStatePostcode());
		Assert.assertEquals(address, pg_POEndereco.getBillingAddress());
		Assert.assertEquals(city+", "+state+" "+postcode, pg_POEndereco.getBillingCityStatePostcode());
		pg_POEndereco.clickOnProcessAddress();
		
		pg_PO_shipping.setTerms();
		priceShipping = pg_PO_shipping.getPriceShipping();
		log.screenshot();
		pg_PO_shipping.clickOnProcessCarrier();

		log.screenshot();
		assertEquals(price, pg_POTipoPagemnto.getTotalProducts(), 0);
		assertEquals(priceShipping, pg_POTipoPagemnto.getTotalShipping(), 0);
		assertEquals(price+priceShipping, pg_POTipoPagemnto.getTotalPrice(), 0);
		pg_POTipoPagemnto.clickOnPayByBankWire();

		log.screenshot();
		pg_POTipoPagemnto.clickOnConfirmOrder();
		
		log.screenshot();
		Assert.assertEquals("Your order on My Store is complete.", pg_POTipoPagemnto.getConfirmation());
		
	}

}

