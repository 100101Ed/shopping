package com.sqa.em.shopping;

import static org.testng.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.sqa.em.util.helper.SelectBrowser;
import com.sqa.em.util.helper.SelectBrowser.BROWSER_TYPE;
import com.sqa.em.util.helper.TakeAndSaveScreenShot;

public class Msn {

	public String BASE_URL = "https://www.msn.com";

	private WebDriver driver;

	public boolean acceptNextAlert = true;

	public StringBuffer verificationErrors = new StringBuffer();

	@AfterClass(enabled = false)
	public void afterClass() {
		// this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	@BeforeClass
	public void beforeClass() {
		this.driver = SelectBrowser.getBrowser(BROWSER_TYPE.fireFox, this.driver, 30);
		this.driver.manage().window().maximize();
		this.driver.get(this.BASE_URL + "/");
	}

	// @DataProvider
	// public Object[][] selectEditorsPickData() {
	// return new Object[][] { new Object[] { 1 }, new Object[] { 2 }, };
	// }
	/**
	 * Will Select the 1st topic of the editors pick and then assert the
	 * comments are between 10 an 100.
	 */
	@Test(enabled = false)
	public void selectEditorsPickAndAssertCommentsCount() {
		// Move to Editors Pick and get all links.
		WebElement menuElm = this.driver.findElement(By.cssSelector("[data-aop*='editorsPick1']"));
		Actions actions = new Actions(this.driver);
		actions.moveToElement(menuElm);
		String[] linkName = menuElm.getText().split("\n");
		actions.perform();
		TakeAndSaveScreenShot.screenShot(this.driver, "test-output/msn/ScreenShots/HomePage.png");
		// click on the 1st topic
		WebElement link = this.driver.findElement(By.linkText(linkName[1]));
		actions.moveToElement(link);
		link.click();
		actions.perform();
		TakeAndSaveScreenShot.screenShot(this.driver, "test-output/msn/ScreenShots/1st Pick.png");
		// Verify that the comments for the topic is between 10 and 100
		WebElement countComments = this.driver.findElement(By.cssSelector("#comment-count"));
		actions.moveToElement(countComments);
		System.out.println("Total Coumments ========> " + countComments.getText());
		String commentsString = countComments.getText();
		actions.perform();
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/msn/ScreenShots/comments section.png");
		int num = getCountOfComments(actions);
		Assert.assertEquals(((num >= 10) && (100 >= num)), true,
				"Comments count(currently has " + num + ") are not within the desired range.\n");
	}

	/**
	 * @param Actions
	 * @return The count of comments for that string.
	 */
	private int getCountOfComments(Actions actions) {
		WebElement countComments = this.driver.findElement(By.cssSelector("#comment-count"));
		actions.moveToElement(countComments);
		System.out.println("Total Coumments ========> " + countComments.getText());
		String commentsString = countComments.getText();
		actions.perform();
		TakeAndSaveScreenShot.screenShot(this.driver,
				"test-output/msn/ScreenShots/comments section.png");
		String countOfCoumments = commentsString.replaceAll("(?:[0-9])", "");
		int num = Integer.parseInt(countOfCoumments);
		return num;
	}

	@Test
	public void selectTopicAndAssertCommentsCount() {
		Actions actions = new Actions(this.driver);
		this.driver.findElement(By.cssSelector(".news>a")).click();
		this.driver.findElement(By.cssSelector(".loaded")).click();
		this.driver.findElement(By.cssSelector(".smalla.index0>a>div>h4")).click();
		int num = getCountOfComments(actions);
		Assert.assertEquals(((num >= 6)), true,
				"Comments count(currently has " + num + ") are not within the minimum range.\n");
	}

	@Test
	public void selectTheTopickYouWantToCountComments() {
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
