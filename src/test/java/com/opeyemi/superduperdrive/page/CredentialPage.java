package com.opeyemi.superduperdrive.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {
    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmit;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "create-credential")
    private WebElement createCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;
    @FindBy(id = "credential-username")
    private WebElement credentialUsername;
    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/th")
    private WebElement displayedCredentialUrl;
    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[2]")
    private WebElement displayedCredentialUsername;
    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[3]")
    private WebElement displayedCredentialPassword;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/button")
    private WebElement editCredentialButton;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr/td[1]/a")
    private WebElement deleteCredentialButton;

    WebDriver webDriver;
    WebDriverWait webDriverWait;

    public CredentialPage(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
        PageFactory.initElements(webDriver, this);
    }




    public void createCredentials(String url, String credUsername, String credPassword) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        navCredentialsTab.click();

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", createCredential);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + url + "';", credentialUrl);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + credUsername + "';", credentialUsername);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + credPassword + "';", credentialPassword);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credentialSubmit);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navCredentialsTab);
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedCredentialUrl));

    }

    public String getDisplayedCredentialUrl() {
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedCredentialUrl));
        return displayedCredentialUrl.getText();
    }

    public String getDisplayedCredentialUsername() {
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedCredentialUsername));
        return displayedCredentialUsername.getText();
    }

    public String getDisplayedCredentialPassword() {
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedCredentialPassword));
        return displayedCredentialPassword.getText();
    }

    public int getCredentialId() {
        webDriverWait.until(ExpectedConditions.visibilityOf(editCredentialButton));
        String attr = editCredentialButton.getAttribute("data-credentialId");
        System.out.println(attr);
        return Integer.parseInt(attr);
    }

    public void viewCredentials() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(editCredentialButton));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editCredentialButton);
        webDriverWait.until(ExpectedConditions.visibilityOf(credentialPassword));
    }

    public String getModalCredentialPassword() {

        return ((JavascriptExecutor) webDriver).executeScript("return arguments[0].value;", credentialPassword).toString();
    }

    public void editCredentials(String editedUrl, String editedCredUsername, String editedCredPassword) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + editedUrl + "';", credentialUrl);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + editedCredUsername + "';", credentialUsername);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + editedCredPassword + "';", credentialPassword);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", credentialSubmit);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navCredentialsTab);
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedCredentialUrl));

    }

    public void deleteCredential() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteCredentialButton);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(navCredentialsTab)).click();
    }

    public boolean isCredentialAvailable() {
        return webDriver.findElements(By.id("username-password")).size() > 0;
    }
}
