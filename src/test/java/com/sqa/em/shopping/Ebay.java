package com.sqa.em.shopping;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Ebay {
	// Declaring variables
	public static String baseURL = "http://www.ebay.com/";
	public static String userName = "testebayautomation@gmail.com";
	public static String userPassword = "testqa123";
	private WebDriver driver;
	public int attemptNum = 0;
	public boolean exitFlag = false;
	public String zeroListings, itemNumber, itemNameOnSearch, itemNameInCart;
	long numListing, itemNumberOnSearchPage, itemNumberOnWatchList;
	public int pageNum = 1;
	public int itemNum = 0;

	@AfterClass
	public void afterClass() {
		// Quit Driver
		// driver.quit();
	}

	@BeforeClass
	public void beforeClass() throws InterruptedException {
		do {
			// Launch FireFox browser
			Thread.sleep(1000);
			driver = new FirefoxDriver();
			// Navigate to url
			Thread.sleep(1000);

			driver.get(baseURL);
			Thread.sleep(1000);
			// Verification if the page is correct
			if (driver.getCurrentUrl().equals(baseURL)) {
				System.out.println("<<<<<<<<>>>>>>>>");
				System.out.println("The page is verified: " + driver.getCurrentUrl());
			} else {
				// In case if the page is wrong then code will make 3 attempts
				// to get the correct page.
				System.out.println("Wrong page!");
				// Quit Driver
				driver.close();
				attemptNum++;
				System.out.println("Attempt: " + attemptNum);
			}
		} while (!(driver.getCurrentUrl().equals(baseURL)) || (attemptNum == 3));
		// Error message if the code couldn't get proper page after 3 attempts
		if (attemptNum == 3) {
			System.out
					.println("Please verify the URL. According the requirements it should be http://www.ebay.com/ \n");
			driver.quit();
		}
		// Login
		driver.findElement(By.cssSelector("#gh-ug>a")).click();
		// Thread.sleep(8000);

		Boolean loginIsVisiable = (new WebDriverWait(driver, 20))
				.until(ExpectedConditions.elementToBeSelected(By.linkText("Email or username")));

		driver.findElement(By.linkText("Email or username")).sendKeys(userName);
		driver.findElement(By.linkText("Password")).sendKeys(userPassword);

		driver.findElement(By.xpath("//input[contains(@placeholder,'Email')]")).sendKeys(userName);
		driver.findElement(By.xpath("//input[contains(@placeholder,'Password')]")).sendKeys(userPassword);
		driver.findElement(By.id("sgnBt")).click();
		Thread.sleep(1000);
	}

	@Test(enabled = false)
	public void cartTest2() throws InterruptedException {
		System.out.println("<<<<<<<<>>>>>>>>");
		System.out.println("===Test 2: Shopping Cart functionality.===");
		Actions actions = new Actions(driver);
		Thread.sleep(3000);
		WebElement mainMenu = driver.findElement(By.xpath("//*[@id='navigationFragment']/div/table/tbody/tr/td[9]"));
		actions.moveToElement(mainMenu).build().perform();
		WebElement subLink = driver.findElement(By.xpath("//a[contains(@title, 'Sporting Goods - Fishing')]"));
		Thread.sleep(3000);
		actions.click(subLink).build().perform();
		driver.findElement(By.xpath("//*[@id='VisualNav']/a[2]/div")).click();

		do {
			Thread.sleep(3000);
			List<WebElement> linksRodsItems = driver.findElements(By.className("vip"));
			linksRodsItems.get(itemNum).click();
			try {
				Thread.sleep(3000);
				if (driver.findElement(By.xpath("//*[@id='isCartBtn_btn']")).isDisplayed()) {
					itemNameOnSearch = driver.findElement(By.id("itemTitle")).getText();
					System.out.println("The name of item on Search page is " + itemNameOnSearch);
					driver.findElement(By.xpath("//*[@id='isCartBtn_btn']")).click();
					if (driver.findElement(By.xpath("//*[@id='pageLevelMessaing']/div/div/i")).isDisplayed()) {
						itemNameInCart = driver
								.findElement(By.xpath("//div[contains(@class, 'ff-ds3 fs16 mb5 fw-n sci-itmttl')]"))
								.getText();
						System.out.println("The name of item in a Cart is " + itemNameInCart);
						if (itemNameOnSearch.equals(itemNameInCart)) {
							System.out.println("Search item and item in a cart are the same.");
							System.out.println("The test was succesfull.");
							System.out.println("<<<<<<<<>>>>>>>>");
							System.out.println("\n");
							exitFlag = true;
						} else {
							Assert.fail("Search item and item in a cart don't match. Test failed.");
							System.out.println("<<<<<<<<>>>>>>>>");
							System.out.println("\n");
							driver.quit();
							System.exit(0);
						}
					}
				}
			} catch (Exception e) {
				driver.navigate().back();
				itemNum++;
			}
		} while (exitFlag == false);
		driver.findElement(By.xpath("//*[@id='gh']/table/tbody/tr/td[1]")).click();
	}

	@DataProvider(name = "test1")
	public Object[][] dp() {
		// TODO: Implement method
		return new Object[][] { { "Test 1", "kshfkajhfkajhdfk", "asjndasndasnd" } };
	}

	@DataProvider(name = "test2")
	public Object[][] dp2() {
		// TODO: Implement method
		return new Object[][] { {} };
	}

	@DataProvider(name = "test3")
	public Object[][] dp3() {
		// TODO: Implement method
		return new Object[][] { {} };
	}

	@Test(enabled = true)
	public void myTest3() throws InterruptedException {
		System.out.println("<<<<<<<<>>>>>>>>");
		System.out.println("===Test 3: My test.===");
		Actions actions = new Actions(driver);
		Thread.sleep(3000);
		WebElement mainMenu = driver.findElement(By.id("gh-ug"));
		actions.moveToElement(mainMenu).build().perform();
		WebElement subLink = driver.findElement(By.linkText("Account settings"));
		Thread.sleep(3000);
		actions.click(subLink).build().perform();
		try {
			if (!(driver
					.findElement(By.xpath("//*[@id='mainContent']/table[4]/tbody/tr[2]/td/table/tbody/tr[2]/td[3]/a")))
							.isDisplayed()) {
				System.out.println("Looks like you don't have any billing info set.");
				System.out.println(
						"Then you actually don't need anything in watch list and cart. LEt me remove everything.");
				driver.findElement(By.id("gh-cart-i")).click();
				List<WebElement> linksItemsToRemove = driver.findElements(By.linkText("Remove"));
				for (WebElement i : linksItemsToRemove) {
					driver.findElement(By.xpath("/a[contains(@linkText, 'Remove')]")).click();
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("The billing info is set. then please go ahead and purchase something! :)");
			System.out.println("Completing the test.");
			System.out.println("<<<<<<<<>>>>>>>>");
			// Quit Driver
			// driver.close();
			driver.quit();
			System.exit(0);
		}

	}

	@Test(enabled = false)
	public void watchListTest1(String testName, String searchItem, String searchItemName) throws InterruptedException {
		System.out.println("<<<<<<<<>>>>>>>>");
		System.out.println("===Test 1: Watch list functionality.===");
		// Verifying if our item exists in Ebay.
		Thread.sleep(700);
		// Search using keyword Java
		driver.findElement(By.id("gh-ac")).sendKeys(searchItem);
		Thread.sleep(700);
		driver.findElement(By.xpath("//*[@id='gh-btn']")).click();
		Thread.sleep(700);
		zeroListings = driver.findElement(By.cssSelector("span.listingscnt")).getText().replaceAll("[^0-9]", "");
		numListing = Integer.parseInt(zeroListings);
		System.out.println("Ebay has " + numListing + " listings that match your search.");

		if (numListing == 0) {
			System.out.println("Ebay doesn't have your item on sale.");
			driver.findElement(By.xpath("//*[@id='gh']/table/tbody/tr/td[1]")).click();
			System.out.println("Completing the test.");
			System.out.println("<<<<<<<<>>>>>>>>");
			driver.findElement(By.xpath("//*[@id='gh-ug']/b[1]")).click();
			driver.findElement(By.xpath("//*[@id='gh-uo']/a")).click();
			// Quit Driver
			// driver.quit();
			// System.exit(0);

		} else {

			// Verifying if there is nothing in Watch list and delete if
			// anything is
			// there
			driver.findElement(By.xpath("//*[@id='gh-eb-My']/div/a[1]")).click();
			driver.findElement(By.xpath("//*[@id='domain-nav']/div[2]/div[2]/ul/li[2]/a")).click();

			try {
				Thread.sleep(3000);
				if (driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div[1]/div[1]/input")).isDisplayed()) {
					driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div[1]/div[1]/input")).click();
					driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div[1]/div[1]/div/a[1]")).click();
					Thread.sleep(1000);
					driver.findElement(By.xpath("//*[@id='delCustpBtnSave']")).click();
				}
			} catch (NoSuchElementException e) {
				System.out.println("The watch list is empty.");
			}

			Thread.sleep(700);
			// Search using keyword Java
			driver.findElement(By.id("gh-ac")).sendKeys(searchItem);
			Thread.sleep(700);
			driver.findElement(By.xpath("//*[@id='gh-btn']")).click();
			Thread.sleep(700);

			// Looking for a book with the keyword
			while ((exitFlag == false) && (pageNum != 4)) {
				Thread.sleep(3000);
				try {
					if (driver.findElement(By.partialLinkText(searchItemName)).isDisplayed()) {
						exitFlag = true;
						// Click on the book if it's displayed
						driver.findElement(By.partialLinkText(searchItemName)).click();
						itemNumber = driver.findElement(By.id("descItemNumber")).getText().replaceAll("[^0-9]", "");
						itemNumberOnSearchPage = Long.parseLong(itemNumber.trim());
						System.out.println("The item number on Search page is " + itemNumberOnSearchPage);
						// break;
					}
				} catch (NoSuchElementException e) {
					System.out.println("Couldn't find your item on page #" + pageNum + ".");
					// Click Next button and search for the book again
					driver.findElement(By.xpath("//*[@id='Pagination']/tbody/tr/td[3]/a")).click();
					pageNum++;
				}
			}

			if (pageNum == 4) {
				System.out.println("Per test requirements, only first three pages should be searched.");
				System.out.println("Completing the test.");
				System.out.println("<<<<<<<<>>>>>>>>");
				// Quit Driver
				// driver.close();
				driver.quit();
				System.exit(0);
			}

			Thread.sleep(500);
			driver.findElement(By.xpath("//*[@id='vi-atl-lnk']/a/span[2]")).click();
			driver.findElement(By.xpath("//*[@id='gh-eb-My']/div/a[1]")).click();
			// Have to replace first extra two and last extra two chars from
			// item
			// number
			Thread.sleep(3000);
			itemNumber = driver.findElement(By.className("display-item-id")).getText().replaceAll("[^0-9]", "");
			itemNumberOnWatchList = Long.parseLong(itemNumber.trim());
			System.out.println("The item number on Watch List is " + itemNumberOnWatchList);

			// Matching the item numbers to figure out if the book is on the
			// watch
			// list
			if (itemNumberOnSearchPage == itemNumberOnWatchList) {
				System.out.println("The book is on watch list.");
			}
			driver.findElement(By.xpath("//*[@id='gh']/table/tbody/tr/td[1]")).click();
			System.out.println("The test was succesfull.");
			System.out.println("<<<<<<<<>>>>>>>>");
			System.out.println("\n");
		}
	}
}