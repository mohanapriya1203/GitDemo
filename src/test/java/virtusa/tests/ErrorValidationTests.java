package virtusa.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import virtusa.testcomponents.Retry;

import virtusa.pageobjects.CartPage;
import virtusa.pageobjects.CheckOutPage;
import virtusa.pageobjects.ConfirmationPage;
import virtusa.pageobjects.ProductCataloguePage;
import virtusa.testcomponents.BaseTest;

public class ErrorValidationTests extends BaseTest {
	
	@Test(groups="ErrorHandling", retryAnalyzer= Retry.class)
	public void loginErrorValidation() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String productName = "ZARA COAT 3";
		landingpage.loginApplication("selenium@uu.com", "Selenim@123");
		Assert.assertEquals(landingpage.getErrorMessage(),"Incorrect email  password.");
}
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String productName = "ZARA COAT 3";
		ProductCataloguePage productCatalogue = landingpage.loginApplication("selenium@uu2.com", "Selenium@123");
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartpage = productCatalogue.goToCartPage();
		Boolean match = cartpage.verifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
	}
}

