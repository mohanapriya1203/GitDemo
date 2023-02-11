package virtusa.stepdefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import virtusa.pageobjects.CartPage;
import virtusa.pageobjects.CheckOutPage;
import virtusa.pageobjects.ConfirmationPage;
import virtusa.pageobjects.LandingPage;
import virtusa.pageobjects.ProductCataloguePage;
import virtusa.testcomponents.BaseTest;

public class StepDefinitionImpl extends BaseTest {
	public LandingPage landingpage;
	public ProductCataloguePage productCatalogue;
	public ConfirmationPage confirmationpage;
	
	@Given("I landed on Ecommerce page")
	public void I_landed_on_Ecommerce_page() throws IOException
	{
		landingpage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_with_username_and_passwrod(String username, String password) {
		productCatalogue = landingpage.loginApplication(username, password);

	}
	
	@When("^I add product (.+) to cart$")
	public void I_add_product_to_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
	}
	
	@When("^checkout (.+) and submit the order$")
	public void checkout_and_submit_the_order(String productName) {
		CartPage cartpage = productCatalogue.goToCartPage();
		Boolean match = cartpage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckOutPage checkoutpage = cartpage.goToCheckout();
		checkoutpage.selectCountry("India");
		confirmationpage = checkoutpage.submitOrder();
	}
	
	@Then("{string} is displayed on the confirmation page.")
	public void confirmationMessage_is_displayed_on_the_confirmation_page(String string) {
		String confirmMessage = confirmationpage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(string)); 
		driver.close();
	}
	
	@Then("{errorMessage} is displayed")
	public void errorMessage_is_displayed(String error) {
		Assert.assertEquals(landingpage.getErrorMessage(),error);
		driver.close();
	}
}
