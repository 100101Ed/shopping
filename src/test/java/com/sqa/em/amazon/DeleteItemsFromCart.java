package com.sqa.em.amazon;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DeleteItemsFromCart {

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
		// TODO: Implement method
		return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" }, };
	}

	@Test(dataProvider = "dp")
	public void f(Integer n, String s) {
		// TODO: Implement method
	}
}
