package com.sqa.em.amazon;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AddItemsToCart {

	@AfterClass
	public void afterClass() {
		// TODO: Implement method
	}

	@BeforeClass
	public void beforeClass() {
		// TODO: Implement method
	}

	@DataProvider
	public Object[][] dp() {
		// TODO: Implement method to have 3 data sets
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}

	@Test(dataProvider = "dp")
	public void f(Integer n, String s) {
		// TODO: Implement method User adds a certain quantity of an item to
		// their cart Validate that the user has added that number of items to
		// their cart.
	}
}
