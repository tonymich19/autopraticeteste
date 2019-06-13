package step;

import static org.junit.Assert.assertEquals;

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
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class Store {
	
	static WebDriver driver;
	static Log log;
	private Double price;
	
	@Dado("^navegador aberto na \"(.*)\" web$")
	public void navegador_aberto_na_web(String url) throws Throwable {
		GenericMethods gem = new GenericMethods();
		gem.getHomePage(url);
		driver = GenericMethods.getDriver();
		log = new Log(driver);
	}
	
	@Dado("\"(.*)\" encontrado$")
	public void encontrado(String item) throws Throwable {
		PO_PaginaPrincipal pg_POPaginaPrincipal = new PO_PaginaPrincipal(driver);
		PO_BuscaResults pg_search_result = new PO_BuscaResults(driver);
		pg_POPaginaPrincipal.setSearchItem(item);
		log.screenshot();
		pg_POPaginaPrincipal.clickOnSubmitSearch();

		log.screenshot();
		pg_search_result.clickOnItem(item);
	}
	
	@Quando("^preencho os dados do item com \"(.*)\", \"(.*)\", \"(.*)\"$")
	public void preencho_os_dados_do_item_com(String quantity_wanted, String size, String color) throws Throwable {
		PO_Produto pg_POProduto = new PO_Produto(driver);
		
		price = pg_POProduto.getPrice()*Double.parseDouble(quantity_wanted);
		
		pg_POProduto.setQuantityWanted(quantity_wanted);
		pg_POProduto.setSize(size);
		pg_POProduto.setColor(color);
		log.screenshot();
	}
	
	@Quando("^adiciono ao carrinho$")
	public void adiciono_ao_carrinho() throws Throwable {
		PO_Produto pg_POProduto = new PO_Produto(driver);
		PO_CartSummary pg_cart_summary = new PO_CartSummary(driver);
		
		pg_POProduto.clickOnAddToCart();
		log.screenshot();
		pg_POProduto.clickOnProceedToCheckout();

		log.screenshot();
		assertEquals(price, pg_cart_summary.getTotalProducts(), 0);
		pg_cart_summary.clickOnProceedToCheckout();	    
	}
	
	@Quando("^crio uma conta com \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\", \"(.*)\"$")
	public void crio_uma_conta_com(String email, String firstname, String lastname, String password, String address, String city, String state, String postcode, String country, String number, String alias) throws Throwable {
		PO_Autenticacao pg_singin_aut = new PO_Autenticacao(driver);
		PO_InfoPessoal pg_singin_per_inf = new PO_InfoPessoal(driver);
		PO_Endereco pg_POEndereco = new PO_Endereco(driver);
		
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
	    
	}
	
	@Quando("^realizo o pagamento via cartao de credito$")
	public void realizo_o_pagamento_via_cartao_de_credito() throws Throwable {

		PO_Shipping pg_PO_shipping = new PO_Shipping(driver);
		PO_TipoPagamento pg_POTipoPagemnto = new PO_TipoPagamento(driver);
		pg_PO_shipping.setTerms();
		double priceShipping = pg_PO_shipping.getPriceShipping();
		log.screenshot();
		pg_PO_shipping.clickOnProcessCarrier();

		log.screenshot();
		assertEquals(price, pg_POTipoPagemnto.getTotalProducts(), 0);
		assertEquals(priceShipping, pg_POTipoPagemnto.getTotalShipping(), 0);
		assertEquals(price+priceShipping, pg_POTipoPagemnto.getTotalPrice(), 0);
		pg_POTipoPagemnto.clickOnPayByBankWire();

		log.screenshot();
		pg_POTipoPagemnto.clickOnConfirmOrder();
	}
	
	@Entao("^visualizo a mensagem de sucesso$")
	public void visualizo_a_mensagem_de_sucesso() throws Throwable {
		PO_TipoPagamento pg_POTipoPagemnto = new PO_TipoPagamento(driver);
		log.screenshot();
		Assert.assertEquals("Your order on My Store is complete.", pg_POTipoPagemnto.getConfirmation());
	}	
}
