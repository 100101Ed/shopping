package com.sqa.em.shopping;

import static org.testng.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sqa.em.util.helper.SelectBrowser;
import com.sqa.em.util.helper.SelectBrowser.BROWSER_TYPE;

public class Msn {

	public String BASE_URL = "https://www.msn.com";

	private WebDriver driver;

	public boolean acceptNextAlert = true;

	public StringBuffer verificationErrors = new StringBuffer();

	@AfterClass(enabled = false)
	public void afterClass() {
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@BeforeClass
	public void beforeClass() {
		this.driver = SelectBrowser.getBrowser(BROWSER_TYPE.fireFox, this.driver, 30);
		this.driver.get(this.BASE_URL + "/");
	}

	@DataProvider
	public Object[][] selectEditorsPickData() {
		return new Object[][] { new Object[] { 1 }, new Object[] { 2 }, };
	}

	@Test(dataProvider = "selectEditorsPickData")
	public void selectEditorsPickTest(Integer editorsPick) {
		this.driver.findElement(By.linkText("EDITORS' PICKS")).click();
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
