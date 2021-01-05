package com.opeyemi.superduperdrive.page;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    WebDriver webDriver;
    WebDriverWait webDriverWait;

    public HomePage(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
        PageFactory.initElements(this.webDriver, this);
    }

    public void logout() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", logoutButton);
    }
}
