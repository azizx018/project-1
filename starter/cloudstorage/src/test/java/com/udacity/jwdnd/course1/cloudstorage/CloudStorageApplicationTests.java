package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;private NotePage notePage;
	private CredentialPage credentialPage;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		notePage = new NotePage(driver);
		credentialPage = new CredentialPage(driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}
	private void doLogout() {
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logoutButton")));
		WebElement logoutButton = driver.findElement(By.id("logoutButton"));
		logoutButton.click();
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the login page.
		assertEquals("http://localhost:" + this.port + "/login?message=You%20successfully%20signed%20up!&status=true", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertTrue(driver.getPageSource().contains("An  error occurred! Please try again later."));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}
	//note tests
	@Test
	public void testAddingANote() {
		//create a test account
		doMockSignUp("add note", "test", "Homer","claw");
		doLogIn("Homer", "claw");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		// fetch the notes tab
		notePage.fetchNotePage(driver);
		//click button to add note
		notePage.addNoteButton(webDriverWait);

		//Try to add a note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		notePage.addNote();

		// fetch the notes tab
		notePage.fetchNotePage(driver);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-view")));

		//check that the values for title and description match
		assertEquals("Hello", notePage.getDisplayTitle());
		assertEquals("Great Day!", notePage.getDisplayDescription());
	}
	@Test
	public void editNote() {
		//create account
		//doMockSignUp("add note", "test", "Homer","claw");
		//login to test account fetch note tab
		doLogIn("Homer", "claw");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		notePage.fetchNotePage(driver);
		//click button to add note
		//notePage.addNoteButton(webDriverWait, driver);
		//add a note
		//webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		//notePage.addTitle();
		//notePage.addDescription();
		//notePage.addNote();
		//logout
		//doLogout();
		//login and go to note tab
		//doLogIn("Homer", "claw");
		//notePage.fetchNotePage(driver);
		//wait until element loads
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editButton")));
		WebElement editNoteButton = driver.findElement(By.id("editButton"));
		webDriverWait.until(ExpectedConditions.elementToBeClickable(editNoteButton)).click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		notePage.editNote();
		notePage.fetchNotePage(driver);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-view")));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description-view")));
		String newNoteTitle = "New note title";
		String newNoteDescription = "New note description";
		assertEquals(newNoteTitle, notePage.getDisplayTitle());
		assertEquals(newNoteDescription, notePage.getDisplayDescription());

	}
	@Test
	public void deleteNote() {
		//create account
		//doMockSignUp("add note", "test", "Homer","claw");
		//login to test account fetch note tab
		doLogIn("Homer", "claw");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		notePage.fetchNotePage(driver);
		//click button to add note
		//notePage.addNoteButton(webDriverWait, driver);
		//add a note
//		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
//		notePage.addTitle();
//		notePage.addDescription();
//		notePage.addNote();
		//get notes tab
		notePage.fetchNotePage(driver);
		//delete note
		notePage.deleteNote(webDriverWait, driver);
		//fetch note page again
		notePage.fetchNotePage(driver);

		List<WebElement> noteRecords = driver.findElements(By.id("note-title-view"));
		assertTrue(noteRecords.isEmpty());

	}
	@Test
	public void addCredential() {
		//create a test account
		//doMockSignUp("add note", "test", "Homer","claw");
		doLogIn("Homer", "claw");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		//Get the credentials page
		credentialPage.fetchCredPage(driver);
		//click button to add a new credential
		credentialPage.addCredButton(webDriverWait);
		//fill in credential form and save credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialPage.addCredential(webDriverWait);

		//fetch credential page once again
		credentialPage.fetchCredPage(driver);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cred-url-display-value")));


		//check that values displayed match
		assertEquals("www.helloWorld.com", credentialPage.getUrlDisplayValue());
		assertEquals("RocketMan", credentialPage.getUsernameDisplayValue());
		assertNotEquals("Rocket", credentialPage.getPasswordDisplayValue());

	}
	@Test
	public void editCredential() {
		//doMockSignUp("add note", "test", "Homer","claw");
		doLogIn("Homer", "claw");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		//get credentials page
		credentialPage.fetchCredPage(driver);

		//click button to add credential
		//credentialPage.addCredButton(webDriverWait);

		//add credentials
		//credentialPage.addCredential(webDriverWait);

		//logout
		//doLogout();

		//login and go to note tab
		//doLogIn("Homer", "claw");
		//credentialPage.fetchCredPage(driver);
		credentialPage.editCredential(webDriverWait);
		credentialPage.fetchCredPage(driver);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cred-url-display-value")));

		String newUrl = "www.newWebTitle.com";
		String newUsername = "VitaminC";
		String newPassword = "Yummy";

		assertEquals(newUrl, credentialPage.getUrlDisplayValue());
		assertEquals(newUsername, credentialPage.getUsernameDisplayValue());
		assertNotEquals(newPassword, credentialPage.getPasswordDisplayValue());

	}
	@Test
	public void deleteCredential() {
		//create account
		//doMockSignUp("add note", "test", "Homer","claw");

		//login to test account fetch note tab
		doLogIn("Homer", "claw");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		//get credential page
		credentialPage.fetchCredPage(driver);

		//add credential
//		credentialPage.addCredButton(webDriverWait);
//		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
//		credentialPage.addCredential(webDriverWait);

		//fetch credential page
		//credentialPage.fetchCredPage(driver);

		//wait for page to load creds and delete cred
		credentialPage.deleteNote(webDriverWait, driver);

		//get credential page
		credentialPage.fetchCredPage(driver);

		List<WebElement> credentialRecords = driver.findElements(By.id("cred-url-display-value"));
		assertTrue(credentialRecords.isEmpty());

	}
}
