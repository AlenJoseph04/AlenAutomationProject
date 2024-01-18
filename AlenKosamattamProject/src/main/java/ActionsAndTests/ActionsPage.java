package ActionsAndTests;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ActionsPage {
WebDriver driver;
	
	
	public void waitToLoad() throws InterruptedException
	{
		Thread.sleep(3000);
	}
	public void lauchTheApp() throws InterruptedException 
	{
		
	
		System.setProperty("webdriver.chrome.driver", "C:\\AlenKosamattamProject\\driverANDexcel\\chromedriver.exe");//paste driver path

		
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.kosamattam.com/");
		driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
		boolean login=driver.findElement(By.xpath("//img[@title='Kosamattam Finance']")).isDisplayed();
		assertEquals(login, true);
		System.out.println("Launched the application, Reached  Koasamattam Finance home page");
		waitToLoad();
	}
	
	
	
	//this is printing all the services provided by kosammatam
	public void verifyServices()
	{
		boolean serviceLabel=driver.findElement(By.xpath("//a[text()='Services']")).isDisplayed(); // checking whether that locator present
		if(serviceLabel)
		{
		
		System.out.println("Services option is present");
		Actions action = new Actions(driver);
		WebElement services = driver.findElement(By.xpath("//a[text()='Services']"));
		action.moveToElement(services).build().perform(); //used to to mouse hover action
		 
		List<WebElement> servicesCount = driver.findElements(By.xpath("(//h2[text()='Services'])[1]//following::ul[1]//li"));// service present in ul->li>a, here we are taking count of list, check this locator in app
		int listCount = servicesCount.size();
		System.out.println("Services provided by Kosammatam finance...................");
		for(int i=1;i<=listCount;i++)
		{
			List<WebElement> elements = driver.findElements(By.xpath("(//h2[text()='Services'])[1]//following::ul[1]//li["+i+"]//a"));//here we are taking count of services present in each list
			int count = elements.size();
			for(int j=1;j<=count;j++)
			{
				String servicesText=driver.findElement(By.xpath("(//h2[text()='Services'])[1]//following::ul[1]//li["+i+"]//a["+j+"]")).getText();
				// (//h2[text()='Services'])[1]//following::ul[1]//li[1]//a[1]-example
				System.out.println(servicesText);
			}	
		}
		}
		else
		{
			System.out.println("Services option is not present");
		}
	}
	
	
	
	public void goldLoanCalculator() throws Exception
	{
		//driver.findElement(By.xpath("//a[text()='Gold Loan Calculator']")).click();
		
		Actions action = new Actions(driver);
		WebElement gold = driver.findElement(By.xpath("//a[text()='Gold Loan Calculator']"));
		action.moveToElement(gold).build().perform();
		driver.findElement(By.xpath("//a[text()='Gold Loan Calculator']")).click();
		
		System.out.println("Clicked on Gold Loan Calculator button");
		boolean goldLoan=driver.findElement(By.xpath("//h2[text()='Gold Calculator']")).isDisplayed();
		assertEquals(goldLoan, true);
		
		
		//	takingDataFromExcel , this code also we can take from google
		File filepath=new File("C:\\AlenKosamattamProject\\driverANDexcel\\Kosammatam_Alen.xlsx");//paste this excel path in your system
		FileInputStream file=new FileInputStream(filepath);
		XSSFWorkbook book=new XSSFWorkbook(file);
	    XSSFSheet sheet=book.getSheet("GoldLoanCalculator");
	    
	    String nameSheet=sheet.getRow(1).getCell(0).getStringCellValue(); // taking data from row one cell 0, cell number and row number startes from 0
	    System.out.println("Name in excel "+nameSheet);
	    String emailSheet=sheet.getRow(1).getCell(1).getStringCellValue();
	    System.out.println("Email in excel sheet "+emailSheet);
	    Integer contactSheet=(int)sheet.getRow(1).getCell(2).getNumericCellValue();
	    System.out.println("Contact details in excel sheet"+contactSheet);
	    String stateSheet=sheet.getRow(1).getCell(3).getStringCellValue();
	    System.out.println("State in excel sheet:"+stateSheet);
	    String citySheet=sheet.getRow(1).getCell(4).getStringCellValue();
	    System.out.println("City in excel sheet:"+citySheet);
	    Integer pincodeSheet=(int)sheet.getRow(1).getCell(5).getNumericCellValue();
	    System.out.println("Pin code in excel sheet:"+pincodeSheet);
	    String goldOrWeight=sheet.getRow(1).getCell(6).getStringCellValue();
	    System.out.println("Gold or weight option in sheet:"+goldOrWeight);
	    String goldTypeSheet=sheet.getRow(1).getCell(7).getStringCellValue();
	    System.out.println("Gold type in sheet:"+goldTypeSheet);
	    Integer goldValueSheet=(int) sheet.getRow(1).getCell(8).getNumericCellValue();
	    System.out.println("Amount or gram you need in sheet:"+goldValueSheet);
		
		driver.findElement(By.name("goldloanCalculator.name")).sendKeys(nameSheet);//name
		driver.findElement(By.id("goldloanCalculator_email")).sendKeys(emailSheet);//email
		driver.findElement(By.id("goldloanCalculator_ContactNumber")).sendKeys(contactSheet.toString());//phone number
		
		Select state = new Select(driver.findElement(By.id("goldloanCalculator_State"))); //state
		state.selectByVisibleText(stateSheet);
		
		driver.findElement(By.id("goldloanCalculator_City")).sendKeys(citySheet);
		driver.findElement(By.id("goldloanCalculator_pincode")).sendKeys(pincodeSheet.toString());
		
		Select mode = new Select(driver.findElement(By.xpath("//select[@id='Calculation_mode']")));
		mode.selectByVisibleText(goldOrWeight);
		
		//to print drop down options
		WebElement web1=driver.findElement(By.id("goldloanCalculator_goldtype"));
        Select goldType=new Select(web1);
        List<WebElement> name=goldType.getOptions();
        System.out.println("Gold type Options...");
        for(WebElement j:name)
        {
        	System.out.println(j.getText()); 
        } //to print drop down options
        goldType.selectByVisibleText(goldTypeSheet);
      
        driver.findElement(By.xpath("//input[@id='goldWeight']")).sendKeys(goldValueSheet.toString());
        driver.findElement(By.xpath("//button[@id='gldsubmitbtn']")).click();
        boolean error=driver.findElement(By.xpath("//span[text()='404']")).isDisplayed();
        
        if(error==false)
        {
        	System.out.println("We received expected result");
        }
        else
        {
        	System.out.println("Recived 404 error Sorry for the inconvenience. We're working on it.");
        	assertEquals(error, false);
        } 
	    
	}
	
	
	
	public void careerPage() throws Exception
	{
		
		boolean career=driver.findElement(By.xpath("(//a[text()='Career'])[1]")).isDisplayed();
		assertEquals(career, true);
		driver.findElement(By.xpath("(//a[text()='Career'])[1]")).click();
		System.out.println("Clicked on Career option");
		boolean careerClick=driver.findElement(By.xpath("//h2[text()='Career']")).isDisplayed();
		assertEquals(careerClick, true);
		System.out.println("Reached Career page");
		
		List<WebElement> careerCount = driver.findElements(By.xpath("//div[@class='career-itm']"));// to find number of job vacancies
		int careerOption = careerCount.size();
		System.out.println(careerOption+" Job vacancies are present, those are");
		for(int i=1;i<=careerOption;i++)
		{
			String careerText=driver.findElement(By.xpath("(//div[@class='career-itm'])["+i+"]//div[@class='img-cntr']//span")).getText();
			System.out.println(careerText);
		}
		
		driver.findElement(By.xpath("(//a[text()='Apply Now'])[1]")).click();
		
		boolean lblPersonalInfo=driver.findElement(By.xpath("//h2[text()='Personal Info']")).isDisplayed();
		assertEquals(lblPersonalInfo, true);
		System.out.println("Clicked on APPLY NOW button and reached on Personal Info"); 
		
		
		////////////////personal info
		System.out.println("Entering personal informations");
		driver.findElement(By.id("firstName")).sendKeys("Wikki");
		
		driver.findElement(By.id("lastName")).sendKeys("Richi");
		
		Select gender = new Select(driver.findElement(By.id("gender"))); //state
		gender.selectByVisibleText("Male");
		///////calender
	
		//driver.findElement(By.xpath("//input[@id='DOB']")).sendKeys("12-12-1999"); // we can directl give date like this other way mentioned below
		selectDateOfBirth(); // written seperate function
		//driver.findElement(By.xpath("//span[@class='ico dob-span slct']")).click(); // to close calender box
		driver.findElement(By.xpath("//input[@id='place']")).sendKeys("Chennai");
		
		Select motherLan = new Select(driver.findElement(By.id("motherTongue"))); //state
		motherLan.selectByVisibleText("Nepali");
		
		Select religion = new Select(driver.findElement(By.xpath("//select[@id='religion']"))); //religion
		religion.selectByVisibleText("Christian");
		
		driver.findElement(By.xpath("//input[@id='cast']")).sendKeys("RC"); //cast
		
		Select category = new Select(driver.findElement(By.id("category"))); //category
		category.selectByVisibleText("General");
		
		Select status = new Select(driver.findElement(By.id("maritalStatus"))); //staus
		status.selectByVisibleText("Single");
		
	
		driver.findElement(By.id("permanentAddress")).sendKeys("home sweet home");
		
		driver.findElement(By.id("presentAddress")).sendKeys("home sweet home");
		Select perState = new Select(driver.findElement(By.xpath("//select[@id='permanentState']"))); //permanentState
		perState.selectByVisibleText("GUJARAT");
		
	
		Select presentState = new Select(driver.findElement(By.xpath("//select[@id='presentState']"))); //presentState
		presentState.selectByVisibleText("GUJARAT");
		

		driver.findElement(By.id("landMark")).sendKeys("Post one");
		driver.findElement(By.id("emailId")).sendKeys("wikki@gmail.com");
		driver.findElement(By.id("PhoneNumber")).sendKeys("1111111111");
		
		
		driver.findElement(By.xpath("//button[text()='Save & Next']")).click();
		
		boolean otherDetails=driver.findElement(By.xpath("//h2[text()='Other Details']")).isDisplayed();
		assertEquals(otherDetails, true);
		System.out.println("Clicked on Save&Next button and reached Other Details page");
		
	}
	
	
	
	
	public void selectDateOfBirth() throws InterruptedException   //date picker
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,150)"); // to scroll till calender we can get this code from goolgle
		
driver.findElement(By.id("DOB")).click();
boolean calender=driver.findElement(By.xpath("//div[@class='datepicker-days']//table")).isDisplayed();
assertEquals(calender, true);
System.out.println("Clicked date of birth calender");
driver.findElement(By.xpath("//th[text()='January 2024']")).click();
driver.findElement(By.xpath("//th[text()='2024']")).click();
driver.findElement(By.xpath("//th[text()='2020-2029']")).click();

WebElement table=driver.findElement(By.xpath("//div[@class='datepicker-decades']//table")); //selecting year gap
List<WebElement> spanElement=table.findElements(By.tagName("span"));

// table have same xpath, in that inside span value is present, //div[@class='datepicker-decades']//table//span
///selecting 2000-2090
for(int a=1;a<=spanElement.size();a++)
{
	String year=spanElement.get(a).getText();
	if(year.contentEquals("2000"))
	{
		spanElement.get(a).click();
		break;
	}
}

waitToLoad();
///selecting year
WebElement tableYear=driver.findElement(By.xpath("//div[@class='datepicker-years']//table")); //selecting year 
List<WebElement> spanYear=tableYear.findElements(By.tagName("span"));
for(int b=1;b<=spanYear.size();b++)
{
	String year=spanYear.get(b).getText();
	if(year.contentEquals("2000"))
	{
		spanYear.get(b).click();
		break;
	}
}

waitToLoad();
///selecting month
WebElement tableMonth=driver.findElement(By.xpath("//div[@class='datepicker-months']//table")); //selecting year gap
List<WebElement> monthSpan=tableMonth.findElements(By.tagName("span"));

for(int i=1;i<=monthSpan.size();i++)
{
	String year=monthSpan.get(i).getText();
	if(year.contentEquals("Apr"))
	{
		monthSpan.get(i).click();
		break;
	}
}

waitToLoad();
///selecting day
WebElement tableDays=driver.findElement(By.xpath("//div[@class='datepicker-days']//table//tbody")); //selecting year gap
List<WebElement> rows=tableDays.findElements(By.tagName("tr"));
boolean selection = false;
for(int i=1;i<=rows.size();i++)
	
{
	WebElement row=driver.findElement(By.xpath("//div[@class='datepicker-days']//table//tbody//tr["+i+"]")); //selecting year gap
	List<WebElement> td=row.findElements(By.tagName("td"));
	
	for(int j=1;j<=td.size();j++)
	{
	String day=driver.findElement(By.xpath("//div[@class='datepicker-days']//table//tr["+i+"]//td["+j+"]")).getText();
	if(day.contentEquals("20"))
	{
		driver.findElement(By.xpath("//div[@class='datepicker-days']//table//tr["+i+"]//td["+j+"]")).click();
		selection=true;
		break;
	}
	
}
	if(selection==true)
	{break;
	
	}
}
	}
	public void driverquit()
	{
		driver.quit();
	}
}
