package com.sqa.em.shopping;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Ebay {
	// Declaring variables
	public static String baseURL = "http://www.ebay.com/";
	public static String userName = "testebayautomation@gmail.com";
	public static String userPassword = "testqa123";
	public static String[] keywords = { "Java", "Thinking", "//*[@id='item464709d46d']/h3/a" };
	private WebDriver driver;
	public int attemptNum = 0;
	boolean foundBook = false;
	public String itemNumberSearchPage, itemNumberWatchList;

	@AfterClass
	public void afterClass() {
		// Quit Driver
		// driver.quit();
	}

	@BeforeClass
	public void beforeClass() throws InterruptedException {
		do {
			// Launch FireFox browser
			driver = new FirefoxDriver();
			// Navigate to url
			driver.get(baseURL);
			Thread.sleep(800);
			// Verification if the page is correct
			if (driver.getCurrentUrl().equals(baseURL)) {
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
		Thread.sleep(1000);
		// Login
		driver.findElement(By.cssSelector("#gh-ug>a")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[contains(@placeholder, 'Email')]")).sendKeys(userName);
		driver.findElement(By.xpath("//input[contains(@placeholder, 'Password')]")).sendKeys(userPassword);
		driver.findElement(By.id("sgnBt")).click();

	}

	@DataProvider(name = "test1")
	public Object[][] dp() {
		// TODO: Implement method
		return new Object[][] { { "Test 1", keywords } };
	}

	@Test(dataProvider = "test1")
	public void test1(String testName, String[] keywords) throws InterruptedException {
		// Verifying if there is nothing in Watch list and delete if anything is
		// there
		// driver.findElement(By.xpath("//*[@id='gh-eb-My']/div/a[1]")).click();
		driver.findElement(By.xpath("//*[@id='gh-eb-My-o']/ul/li[3]/a")).click();
		Thread.sleep(1000);
		if (driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div")).isDisplayed()) {
			Thread.sleep(700);
			// Search using keyword Java
			driver.findElement(By.id("gh-ac")).sendKeys(keywords[0]);
			Thread.sleep(700);
			driver.findElement(By.xpath("//*[@id='gh-btn']")).click();
			Thread.sleep(700);
		} else if (driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div[1]/div[1]/input")).isDisplayed()) {
			driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div[1]/div[1]/input")).click();
			driver.findElement(By.xpath("//*[@id='watchlist']/div[2]/div[1]/div[1]/div/a[1]")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//*[@id='delCustpBtnSave']")).click();
		}

		// Looking for a book with the keyword 'Thinking'
		do {
			if (driver.findElement(By.xpath(keywords[2])).isDisplayed()) {
				foundBook = true;
				// Click on the book if it's displayed
				driver.findElement(By.xpath(keywords[2])).click();
				itemNumberSearchPage = driver.findElement(By.id("descItemNumber")).getText();
				System.out.println("The Search item number is " + itemNumberSearchPage);
			} else {
				// Click Next button and search for the book again
				driver.findElement(By.xpath("//*[@id='Pagination']/tbody/tr/td[3]/a")).click();
			}
		} while (foundBook == false);
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id='vi-atl-lnk']/a/span[2]")).click();
		driver.findElement(By.xpath("//*[@id='gh-eb-My']/div/a[1]")).click();
		// Have to replace first extra two and last extra two chars from item
		// number
		itemNumberWatchList = driver.findElement(By.className("display-item-id")).getText().replace("(", "");
		String updatedItemNumberWatchList = itemNumberWatchList.replace(" )", "");
		System.out.println("The Search item number is " + updatedItemNumberWatchList);
		// Matching the item numbers to figure out if the book is on the watch
		// list
		if (updatedItemNumberWatchList.equals(itemNumberSearchPage)) {
			System.out.println("The book is on watch list./n");
		}
		System.out.println("The test was succesfull.");
	}
}
