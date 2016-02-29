/**
 * File Name: readFromFile.java<br>
 * Mora, Eduardo<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 22, 2016
 */
package com.sqa.em.util.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * readFromFile //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Mora, Eduardo
 * @version 1.0.0
 * @since 1.0
 */
public class ReadFromFile {

	static String locationOfFile;

	/**
	 * @param args
	 */
	public static String getRows(int rows, String fileLocation) {
		String[] tem = null;
		String everything = null;
		try (BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			everything = sb.toString();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return everything;
	}
}
