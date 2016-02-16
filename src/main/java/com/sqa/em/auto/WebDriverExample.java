/**
 * File Name: WebDriverExample.java<br>
 * Mora, Eduardo<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 13, 2016
 */
package com.sqa.em.auto;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * WebDriverExample //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Mora, Eduardo
 * @version 1.0.0
 * @since 1.0
 */
public class WebDriverExample {

	private static String baseURL = "http://msn.com";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		WebDriver driver = new FirefoxDriver();
		driver.get(baseURL);
		System.out.println("Your are currently on the page " + driver.getTitle());
		System.out.println(driver.getCurrentUrl() + "\n" + driver.getPageSource());
		List<WebElement> links = driver.findElements(By.tagName("a"));
		for (WebElement webElement : links) {
			System.out.println(webElement.getText() + ":" + webElement.getAttribute("href"));
		}
	}
}
