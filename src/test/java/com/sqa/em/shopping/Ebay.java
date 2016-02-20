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
			Thread.sleep(500);
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
		Thread.sleep(800);
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
		// Search using keyword Java
		driver.findElement(By.id("gh-ac")).sendKeys(keywords[0]);
		driver.findElement(By.id("gh-btn")).click();
		Thread.sleep(700);
		// Looking for a book with the keyword 'Thinking'
		do {
			if (driver.findElement(By.xpath(keywords[2])).isDisplayed()) {
				foundBook = true;
				// Click on the book if it's displayed
				driver.findElement(By.xpath(keywords[2])).click();
			} else {
				// Click Next button and search for the book again
				driver.findElement(By.xpath("//*[@id='Pagination']/tbody/tr/td[3]/a")).click();
			}
		} while (foundBook == false);
		Thread.sleep(500);
		driver.findElement(By.xpath("//*[@id='vi-atl-lnk']/a/span[2]")).click();
		driver.findElement(By.xpath("//*[@id='gh-eb-My']/div/a[1]")).click();
		if (driver
				.findElement(
						By.xpath("//*[@id='watchlist']/div[2]/div[3]/div[1]/table/tbody/tr/td[2]/div/div[2]/div[1]/a"))
				.isDisplayed()) {
			System.out.println("The book is in watch list.");
			driver.findElement(By.xpath("//*[@id='gh-uo']/a")).click();
		} else {
			// Click Next button and search for the book again
			System.out.println("Sorry, book was not added to the watch list or simply doesn't exists.");
			driver.findElement(By.xpath("//*[@id='gh-uo']/a")).click();
		}

	}
}
