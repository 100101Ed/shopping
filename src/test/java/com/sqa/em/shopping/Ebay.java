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
	private WebDriver driver;
	public int attemptNum = 0;

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
			}
		} while (!(driver.getCurrentUrl().equals(baseURL)) || (attemptNum == 3));
		// Error message if the code couldn't get proper page after 3 attempts
		if (attemptNum == 3) {
			System.out
					.println("Please verify the URL. According the requirements it should be http://www.ebay.com/ \n");
			driver.quit();
		}

		driver.findElement(By.cssSelector("#gh-ug>a")).click();
		// driver.findElement(By
		// .xpath("html/body/div[4]/div/div/div/div[5]/div/div[1]/div/div[2]/div[1]/div[2]/span/form/div[1]/div[2]/div[4]/span[2]/input"))
		// .sendKeys(userName);
		// driver.findElement(By
		// .xpath("html/body/div[4]/div/div/div/div[5]/div/div[1]/div/div[2]/div[1]/div[2]/span/form/div[1]/div[2]/div[5]/span[2]/input"))
		// .sendKeys(userPassword);
		driver.findElement(By.xpath("//input[contains(@placeholder, 'Email')]")).sendKeys(userName);
		driver.findElement(By.xpath("//input[contains(@placeholder, 'Password')]")).sendKeys(userPassword);
		driver.findElement(By.id("sgnBt")).click();
	}

	@DataProvider
	public Object[][] dp() {
		// TODO: Implement method
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}

	@Test(dataProvider = "dp")
	public void f(Integer n, String s) {
		// TODO: Implement method
	}
}
