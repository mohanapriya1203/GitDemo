package virtusa.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import virtusa.abstractcomponents.AbstractComponent;

public class ProductCataloguePage extends AbstractComponent {
	WebDriver driver;
	
	public ProductCataloguePage(WebDriver driver) {
		super(driver);
		this.driver = driver;
				//this.driver refers to current class instant variable which is declared at the top.
		PageFactory.initElements(driver, this);
	}

	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	By productsBy = By.cssSelector(".mb-3"); //By.cssSelector() -- this is called 'By' Locator.
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}
	
	public WebElement getProductByName(String productName) {
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).
				getText().equals(productName)).findFirst().orElse(null);
		return prod;
	}
	
	public void addProductToCart(String ProductName) throws InterruptedException {
		WebElement prod = getProductByName(ProductName);
		prod.findElement(addToCart).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}

}
