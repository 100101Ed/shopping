/**
 * File Name: TakeAndSaveScreenShot.java<br>
 * Mora, Eduardo<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 27, 2016
 */
package com.sqa.em.util.helper;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * TakeAndSaveScreenShot //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 * 
 * @author Mora, Eduardo
 * @version 1.0.0
 * @since 1.0
 */
public class TakeAndSaveScreenShot {

	/**
	 * Will take a screen shot and save to the locate as well with the specified
	 * name.type
	 * 
	 * @param driver
	 * @param fileLocationAndName
	 *            make usre to also include the file type after the name
	 *            (.png...)
	 */
	static public void screenShot(WebDriver driver, String fileLocationAndName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(fileLocationAndName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
