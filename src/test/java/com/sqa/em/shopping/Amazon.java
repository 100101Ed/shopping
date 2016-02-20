package com.sqa.em.shopping;

import static org.testng.Assert.fail;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sqa.em.util.helper.RequestInput;

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

	private WebDriver driver;

	private String email = "";

	private String password = "";

	private String baseUrl;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	@Test(enabled = false, dataProvider = "dp")
	public void add3ItemsToCartTest(Integer n, String s) {
		// TODO: Implement method. Verify cost comes up to what they were
		// expecting.
	}

	@DataProvider
	public Object[][] addQuanityToCartData() {
		// TODO: Implement method to have 3 data sets
		// return new Object[][] { new Object[] { 1 }, new Object[] { 3 } };
		return new Object[][] { new Object[] { 3 } };
	}

	@Test(dataProvider = "addQuanityToCartData")
	public void addQuanityToCartTest(Integer numOfItems) throws InterruptedException {
		for (int i = 0; i < numOfItems; i++) {
			this.driver.findElement(By.xpath(".//input[@id='twotabsearchtextbox']")).clear();
			this.driver.findElement(By.xpath(".//input[@id='twotabsearchtextbox']")).sendKeys("computers");
			this.driver.findElement(By.xpath(".//input[@id='nav-search']/form/div[2]/div/input")).click();
			this.driver.findElement(By.xpath(".//input[@id='result_0']/div/div[2]/div[1]/a/h2")).click();
			this.driver.findElement(By.xpath(".//input[@id='add-to-cart-button']")).click();
			System.out.println(" Looking for an object ");
		}
	}

	@AfterClass
	public void afterClass() {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@BeforeClass
	public void beforeClass() {
		if (this.password == "") {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter the the following Details to Login to your Amazon account!");
			setEmail(RequestInput.getString("Email: "));
			setPassword(RequestInput.getString("Password: "));
			scanner.close();
		}
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.amazon.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test(enabled = false, dataProvider = "dp")
	public void deleteItemsFromCart(Integer n, String s) {
		// TODO: Implement method . User deleted a number of items of their cart
		// if there are items. Validate that the items were deleted.
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

	@BeforeMethod
	public void logIn() {
		this.driver.get(this.baseUrl + "/");
		this.driver.findElement(By.xpath(".//a[@id='nav-link-yourAccount']")).click();
		// Enter email
		this.driver.findElement(By.xpath(".//input[@id='ap_email']")).clear();
		this.driver.findElement(By.xpath(".//input[@id='ap_email']")).sendKeys(getEmail());
		// Enter password
		this.driver.findElement(By.xpath(".//input[@id='ap_password']")).clear();
		this.driver.findElement(By.xpath(".//input[@id='ap_password']")).sendKeys(getPassword());
		this.driver.findElement(By.xpath(".//input[@id='signInSubmit']")).click();
	}

	@AfterMethod()
	public void logOut() throws InterruptedException {
		Thread.sleep(5000);
		this.driver.findElement(By.xpath(".//*[@id='nav-link-yourAccount']")).click();
		this.driver.findElement(By.xpath(".//*[@id='nav-item-signout']/span")).click();
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
