package com.sqa.em.shopping;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sqa.em.util.helper.TakeAndSaveScreenShot;

/**
 * Amazon //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Mora, Eduardo
 * @version 1.0.0
 * @since 1.0 1. User adds a certain quantity of an item to their cart Validate
 *        that the user has added that number of items to their cart. 2. User
 *        adds three items to their cart. Validate that the total cost comes out
 *        to what they would expect to have in their shopping cart. 3. (Create
 *        your own test case) User deleted a number of items of their cart if
 *        there are items. Validate that the items were deleted.
 */
//
public class Amazon {

	public String BASE_URL_AMAZON = "https://www.amazon.com";

	private WebDriver driver;

	public String email = "";

	public String password = "";

	public boolean acceptNextAlert = true;

	public StringBuffer verificationErrors = new StringBuffer();

	@DataProvider
	public Object[][] addItemsToCartData() {
		return new Object[][] { new Object[] { 3 } };
	}

	@Test(enabled = true, dataProvider = "addItemsToCartData")
	public void addItemsToCartTest(Integer numOfItems) {
		int startCountOfCart = Integer.parseInt(
				this.driver.findElement(By.xpath("//a[@id='nav-cart']/span[4]")).getText());
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/amazon/ScreenShots/BeforeAddingItems.png");
		addItems(numOfItems, "computer");
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/amazon/ScreenShots/AfterAddingItems.png");
		int endCountOfCart = Integer.parseInt(
				this.driver.findElement(By.xpath("//a[@id='nav-cart']/span[4]")).getText());
		int itemsAddedToCart =
				(startCountOfCart == 0) ? endCountOfCart : endCountOfCart - startCountOfCart;
		assertEquals(itemsAddedToCart, (int) numOfItems);
	}

	private void addItems(Integer numOfItems, String searchText) {
		for (int i = 0; i < numOfItems; i++) {
			this.driver.findElement(By.xpath(".//input[@id='twotabsearchtextbox']")).clear();
			this.driver.findElement(By.xpath(".//input[@id='twotabsearchtextbox']"))
					.sendKeys(searchText);
			this.driver.findElement(By.id("nav-search-submit-text")).click();
			this.driver.findElement(By.cssSelector(
					".a-size-base.a-color-null.s-inline.s-access-title.a-text-normal"));
			this.driver.findElement(By.xpath(".//.[@id='result_0']/div/div[2]/div[1]/a/h2"))
					.click();
			// CSS #add-to-cart-button-ubb,#add-to-cart-button,
			this.driver.findElement(By.xpath(".//input[contains(@id,'add-to-cart-button')]"))
					.click();
		}
	}

	@AfterClass
	public void afterClass() {
		this.driver.findElement(By.xpath(".//*[@id='nav-link-yourAccount']")).click();
		this.driver.findElement(By.xpath(".//*[@id='nav-item-signout']/span")).click();
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@BeforeClass
	public void beforeClass() {
		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Hangs with a database connection TODO: Fix DB later or get from File
		// or Pom.
		// String[] userDetails = DataBaseUtil.getData();
		// setPassword(userDetails[0]);
		// setEmail(userDetails[1]);
		this.driver.get(this.BASE_URL_AMAZON + "/");
		this.driver.findElement(By.xpath(".//a[@id='nav-link-yourAccount']")).click();
		// Enter email
		this.driver.findElement(By.xpath(".//input[@id='ap_email']")).clear();
		this.driver.findElement(By.xpath(".//input[@id='ap_email']")).sendKeys(getEmail());
		// Enter password
		this.driver.findElement(By.xpath(".//input[@id='ap_password']")).clear();
		this.driver.findElement(By.xpath(".//input[@id='ap_password']")).sendKeys(getPassword());
		this.driver.findElement(By.xpath(".//input[@id='signInSubmit']")).click();
	}

	// cartNotExceedsCost
	@DataProvider
	public Object[][] cartNotExceedsCostData() {
		return new Object[][] { new Object[] { 5.00 }, new Object[] { 6000.00 } };
	}

	@Test(enabled = true, dataProvider = "cartNotExceedsCostData")
	public void cartNotExceedsCostTest(Double expectedCost) {
		double startCost = 0;
		double finalCost = 0;
		int numOfItems = 3;
		int startCountOfItemsInCart = 0;
		int itemsAddedToCart = 0;
		int endCountOfCart = 0;
		startCost = getCurrentTotalCostOnCart();
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/amazon/ScreenShots/OriginalAmount.png");
		addItems(3, "computers");
		finalCost = getCurrentTotalCostOnCart();
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/amazon/ScreenShots/FinalAmount.png");
		System.out.println("Inital Cost before adding items = $" + startCost);
		System.out.println("Final Cost after adding items = $" + finalCost);
		System.out.println("Difference of cost based on items added = $" + (finalCost - startCost));
		System.out.println("Budget for new items added to cart = $" + expectedCost);
		Assert.assertTrue(expectedCost >= (finalCost - startCost));
	}

	/**
	 * @param currentCost
	 */
	private Double getCurrentTotalCostOnCart() {
		if ((Integer.parseInt(this.driver.findElement(By.id("nav-cart-count")).getText())) > 0) {
			String delimiter = " |\\n|\\t";
			// System.out.println("----------->Cart Is not
			// Empty<------------------");
			this.driver.findElement(By.id("nav-cart")).click();
			String text = this.driver.findElement(By.id("gutterCartViewForm")).getText();
			// System.out.println(" Elements ==> " + text);
			String[] string = text.split(delimiter);
			for (String string2 : string) {
				// System.out.println("String split results " + string2);
				if (string2.contains("$")) {
					string2 = string2.replace("$", "");
					string2 = string2.replace(",", "");// over $1,000
					return Double.parseDouble(string2);
				}
			}
		} else {
			return 0.0;
		}
		return null;
	}

	@DataProvider
	public Object[][] deleteItemsFromCartData() {
		return new Object[][] { new Object[] { 1 } };
	}

	@Test(enabled = true, dataProvider = "deleteItemsFromCartData")
	public void deleteItemsFromCartTest(Integer itemsToDelete) {
		// TODO: Implement method . User deleted a number of items of their cart
		// if there are items. Validate that the items were deleted.
		// CSS .a-declarative>input
		// xpath
		// .//*[@id='activeCartViewForm']/div[2]/div[1]/div[4]/div[2]/div[1]/div/div/div[2]/div/span[1]/span/input
		/**
		 * <input type="submit" aria-label=
		 * "Delete Dell OptiPlex 760 Desktop Complete Computer Package - 4GB Memory Windows 7 , Keyboard &amp; Mouse - Mouse and Dell 17&quot; LCD"
		 * value="Delete" name="submit.delete.C1MQWZVNP1QGMK">
		 */
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/amazon/ScreenShots/BeforeDeletingItems.png");
		int itemsInCart = Integer.parseInt(
				this.driver.findElement(By.xpath("//a[@id='nav-cart']/span[4]")).getText());
		if ((itemsInCart < 1)) {
			System.out.println("There are zero Items in Cart");
			Assert.fail();
		} else {
			if (itemsToDelete < 1) {
				System.out.println("Nothing to Delete :-)");
			} else {
				if (itemsToDelete > itemsInCart) {
					System.out.println(
							"There are less items in the Cart than the amount needed to delete");
					Assert.fail();
				} else {
					for (int i = 0; i < itemsToDelete; i++) {
						this.driver.findElement(By.id("nav-cart")).click();
						this.driver.findElement(By.id("a-autoid-2-announce")).click();
						this.driver.findElement(By.id("dropdown1_0")).click();
						this.driver.findElement(By.xpath(".//input[@value='Delete']")).click();
					}
					TakeAndSaveScreenShot.screenShot(this.driver,
							"test-output/amazon/ScreenShots/AfterDeletingItems.png");
				}
			}
		}
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private boolean isElementPresent(By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
}
