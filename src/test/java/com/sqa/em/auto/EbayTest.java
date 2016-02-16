/**
 * File Name: TestWebDriver.java<br>
 * Mora, Eduardo<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 13, 2016
 */
package com.sqa.em.auto;

import static org.testng.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EbayTest {

	private WebDriver driver;

	private String baseUrl;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		this.driver = new SafariDriver();
		this.baseUrl = "http://www.ebay.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	// @BeforeClass(alwaysRun = true)
	// public void setUp() throws Exception {
	// System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
	// this.driver = new InternetExplorerDriver();
	// this.baseUrl = "http://www.ebay.com/";
	// this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// }
	// @BeforeClass(alwaysRun = true)
	// public void setUp() throws Exception {
	// System.setProperty("webdriver.chrome.driver",
	// "drivers/chromedriver.exe");
	// this.driver = new ChromeDriver();
	// this.baseUrl = "http://www.ebay.com/";
	// this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// }

	// @BeforeClass(alwaysRun = true)
	// public void setUp() throws Exception {
	// // this.driver = new FirefoxDriver();
	// this.driver = new ChromeDriver();
	// this.baseUrl = "http://www.ebay.com/";
	// this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	// }
	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@Test
	public void testEbay() throws Exception {
		this.driver.get(this.baseUrl + "/");
		this.driver.findElement(By.linkText("Sign in")).click();
		this.driver.findElement(By.id("userid")).clear();
		this.driver.findElement(By.id("userid")).sendKeys("kathy.us@aol.com");
		this.driver.findElement(By.id("pass")).clear();
		this.driver.findElement(By.id("pass")).sendKeys("t3stmail");
		this.driver.findElement(By.id("csi")).click();
		this.driver.findElement(By.id("sgnBt")).click();
		this.driver
				.findElement(By.cssSelector(
						"a[alt=\"KitchenAid Stand Mixer...\"] > span.n1_mfbb_dd_ndb_vr_656_spn > span.n1_mfbb_dd_ndb_vr_656_img.n1_mfbb_dd_ndb_vr_656_img_spn > img"))
				.click();
		this.driver.findElement(By.id("gh-ac")).click();
		this.driver.findElement(By.id("gh-ac")).clear();
		this.driver.findElement(By.id("gh-ac")).sendKeys("cup");
		this.driver.findElement(By.id("gh-btn")).click();
		this.driver
				.findElement(By.cssSelector(
						"img[alt=\"Lot 2 75ml Stainless Steel Travel Portable Folding Collapsible Cup Portable New\"]"))
				.click();
		this.driver.findElement(By.cssSelector("span.vi-atw-txt")).click();
		this.driver.findElement(By.cssSelector("span.vi-rmw-txt")).click();
		this.driver.findElement(By.cssSelector("b.gh-eb-arw.gh-sprRetina")).click();
		this.driver.findElement(By.cssSelector("b.gh-eb-arw.gh-sprRetina")).click();
		this.driver.findElement(By.linkText("Sign out")).click();
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
