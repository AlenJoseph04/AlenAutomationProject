package ActionsAndTests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestPage {
ActionsPage action=new ActionsPage();
	
	@BeforeTest
	public void Setup() throws InterruptedException {

		action.lauchTheApp();
	}

	
@Test(priority = 1)
	
	public void GoldLoanCalculator() throws Exception
	
	{
	action.goldLoanCalculator();
	
	
	}

	@Test(priority = 2)
	
	public void verifyServices() throws Exception
	{
	
		action.verifyServices();
	
	}
	
@Test(priority = 3)
	
	public void CareerOperation() throws Exception
	{
	
	action.careerPage();
	
	}
	
	@AfterTest
	public void afterTest()
	{
		action.driverquit();
	}
}
