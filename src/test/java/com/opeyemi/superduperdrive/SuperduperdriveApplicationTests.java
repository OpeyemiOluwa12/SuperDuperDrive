package com.opeyemi.superduperdrive;

import com.opeyemi.superduperdrive.mapper.CredentialsMapper;
import com.opeyemi.superduperdrive.model.Credentials;
import com.opeyemi.superduperdrive.page.*;
import com.opeyemi.superduperdrive.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SuperduperdriveApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private WebDriverWait wait;

    public String BASE_URL;

    @Autowired
    CredentialsMapper credentialsMapper;

    @Autowired
    EncryptionService encryptionService;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        BASE_URL = "http://localhost:" + port;
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get(BASE_URL + "/login");
        assertEquals("Login", driver.getTitle());
    }

    @Test
    public void testSignUpAndLogin() {
        signupAndLoginProcess();

        assertEquals(BASE_URL + "/home", driver.getCurrentUrl());

        HomePage homePage = new HomePage(driver, wait);
        homePage.logout();

        wait.until(ExpectedConditions.urlContains(BASE_URL + "/login"));

        assertNotEquals(BASE_URL + "/home", driver.getCurrentUrl());

    }

    @Test
    public void NoteCreation() {
        // Sign up and login user
        signupAndLoginProcess();

        // Note creation details
        String noteTitle = "New Note";
        String noteDescription = "Testing selenium";

        //verify that home page is loaded
        wait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        // Create note with note details
        NotePage notePage = new NotePage(driver, wait);
        notePage.createNote(noteTitle, noteDescription);

        //Verify that displayed note is correct
        assertEquals(noteTitle, notePage.getDisplayedNoteTitle());
        assertEquals(noteDescription, notePage.getDisplayedNoteDescription());

        // Edited note details
        String editedNoteTitle = "New Note2";
        String editedNoteDescription = "Testing selenium overall";

        // Verify that edited note is displayed correctly
        notePage.editNote(editedNoteTitle, editedNoteDescription);
        assertEquals(editedNoteTitle, notePage.getDisplayedNoteTitle());
        assertEquals(editedNoteDescription, notePage.getDisplayedNoteDescription());

        // Delete note
        notePage.deleteNote();
        assertNotEquals(true, notePage.isNoteAvailable());

    }

    @Test
    public void credentials() {
        //Sign up and login user
        signupAndLoginProcess();


        // credential details
        String url = "www.opeyemi.com";
        String credUsername = "Olamide";
        String credPassword = "whatever";


        //verify that home page is loaded
        wait.until(ExpectedConditions.titleContains("Home"));
        Assertions.assertEquals("Home", driver.getTitle());

        // create credential with credential details
        CredentialPage credentialPage = new CredentialPage(driver, wait);
        credentialPage.createCredentials(url, credUsername, credPassword);

        // Get saved credential details
        Credentials credentials = credentialsMapper.getCredentialsByCredId(credentialPage.getCredentialId());

        //verify that displayed credential password is equals to save credential password
        assertEquals(credentials.getPassword(), credentialPage.getDisplayedCredentialPassword());
        // Verify that displayed credential is encrypted
        String decryptedPassword = encryptionService.decryptValue(credentialPage.getDisplayedCredentialPassword(), credentials.getKey());
        assertEquals(credPassword, decryptedPassword);

        // view credential in a model
        credentialPage.viewCredentials();
        String viewedModalPassword = credentialPage.getModalCredentialPassword();
        // verify viewed credential password is unencrypted
        assertEquals(credPassword, viewedModalPassword);

        // Edited credential details
        String editedUrl = "www.opeyemi.edu.ng";
        String editedCredUsername = "Opeyemi";
        String editedCredPassword = "whateverYouSay";

        //Edit credentials
        credentialPage.editCredentials(editedUrl, editedCredUsername, editedCredPassword);

        // Get saved edited credential details
        Credentials editedCredentials = credentialsMapper.getCredentialsByCredId(credentialPage.getCredentialId());

        //Verify that displayed credential is same and edited credential
        assertEquals(editedCredentials.getPassword(), credentialPage.getDisplayedCredentialPassword());
        String editedDecryptedPassword = encryptionService.decryptValue(credentialPage.getDisplayedCredentialPassword(), editedCredentials.getKey());
        assertEquals(editedCredPassword, editedDecryptedPassword);

        assertEquals(editedCredUsername, credentialPage.getDisplayedCredentialUsername());
        assertEquals(editedUrl, credentialPage.getDisplayedCredentialUrl());

        //Delete credential
        credentialPage.deleteCredential();
        //Verify that deleted credential is no longer available
        assertNotEquals(true, credentialPage.isCredentialAvailable());

    }

    /**
     * Verify that unauthorized users cant access other page except the login and signup page.
     */

    @Test
    public void unAuthorizedUsers() {

        driver.get(BASE_URL + "/home");
        assertNotEquals(BASE_URL + "/home", driver.getCurrentUrl());
        assertEquals(BASE_URL + "/login", driver.getCurrentUrl());

        driver.get(BASE_URL + "/signup");
        assertEquals(BASE_URL + "/signup", driver.getCurrentUrl());

        driver.get(BASE_URL + "/login");
        assertEquals(BASE_URL + "/login", driver.getCurrentUrl());
    }

    /**
     * Sign up and login user
     */
    private void signupAndLoginProcess() {
        String username = "OpeyemiOluwa";
        String password = "12345";
        String lastName = "Idris";
        String firstName = "Opeyemi";

        driver.get(BASE_URL + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp(firstName, lastName, username, password);

        driver.get(BASE_URL + "/loginup");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
    }

}
