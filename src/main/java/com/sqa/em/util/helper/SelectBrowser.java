/**
 * File Name: SelectBrowser.java<br>
 * Mora, Eduardo<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 28, 2016
 */
package com.sqa.em.util.helper;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/**
 * SelectBrowser //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author Mora, Eduardo
 * @version 1.0.0
 * @since 1.0
 */
public class SelectBrowser {

	static public enum BROWSER_TYPE {
		fireFox, internetExplorer, googleChrome
	}

	static public WebDriver getBrowser(BROWSER_TYPE browser, WebDriver driver, int inpliciteWait) {
		switch (browser) {
		case fireFox:
			driver = new FirefoxDriver();
			break;
		case internetExplorer:
			System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			break;
		case googleChrome:
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		default:
			System.out.println("Switch Statement needs to be updated because browser(" + browser
					+ ") requested was not found?");
			break;
		}
		if (inpliciteWait > 0) {
			driver.manage().timeouts().implicitlyWait(inpliciteWait, TimeUnit.SECONDS);
		}
		return driver;
	}
}
