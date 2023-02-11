package virtusa.tests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import virtusa.abstractcomponents.OrderPage;
import virtusa.pageobjects.CartPage;
import virtusa.pageobjects.CheckOutPage;
import virtusa.pageobjects.ConfirmationPage;
import virtusa.pageobjects.LandingPage;
import virtusa.pageobjects.ProductCataloguePage;
import virtusa.testcomponents.BaseTest;

public class SubmitOrderTest extends BaseTest {
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData", groups={"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ProductCataloguePage productCatalogue = landingpage.loginApplication(input.get("email"), input.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartpage = productCatalogue.goToCartPage();
		Boolean match = cartpage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckOutPage checkoutpage = cartpage.goToCheckout();
		checkoutpage.selectCountry("India");
		ConfirmationPage confirmationpage = checkoutpage.submitOrder();
		String confirmMessage = confirmationpage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER.")); //As the message is present in capital letters, we are using like this to verify.
		
	}
	
	@Test(dependsOnMethods={"submitOrder"})
	public void OrderHistoryTest() {
		ProductCataloguePage productCatalogue = landingpage.loginApplication("selenium@uu.com", "Selenium@123");
		OrderPage orderpage = productCatalogue.goToOrderPage();
		Assert.assertTrue(orderpage.verifyOrderDisplay(productName));
	}
	
	@DataProvider
	public Object[][] getData() throws IOException {
		
		List<HashMap<String, String>> data = getJsonDataMap(System.getProperty("user.dir")+"\\src\\test\\java\\virtusa\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
	
	
	
	/*
	 @DataProvider
	public Object[][] getData() {
		return new Object[][] {{"selenium@uu.com", "Selenium@123","ZARA COAT 3"}, {"selenium@uu2.com", "Selenium@123","ADIDAS ORIGINAL"}};
	}
	 
	@DataProvider
	public Object[][] getData() throws IOException {
		/*HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "selenium@uu.com");
		map.put("password","Selenium@123");
		map.put("product","ZARA COAT 3");
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "selenium@uu2.com");
		map1.put("password","Selenium@123");
		map1.put("product","ADIDAS ORIGINAL"); 
		retun new Object[][] {{map1}, {map2}};
		}
	*/

}

