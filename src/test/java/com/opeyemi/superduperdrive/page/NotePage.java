package com.opeyemi.superduperdrive.page;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    @FindBy(id = "create-note")
    private WebElement createNote;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "noteSubmit")
    private WebElement noteSubmit;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/th")
    private WebElement displayedNoteTitle;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[2]")
    private WebElement displayedNoteDescription;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr[1]/td[1]/button")
    private WebElement editNoteButton;

    @FindBy(xpath = "//*[@id=\"titleDescription\"]/td[1]/a")
    private WebElement deleteNoteButton;

    WebDriverWait webDriverWait;
    WebDriver webDriver;

    public NotePage(WebDriver webDriver, WebDriverWait webDriverWait) {
        this.webDriverWait = webDriverWait;
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }


    public void createNote(String title, String description) {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        navNotesTab.click();

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", createNote);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + title + "';", noteTitle);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + description + "';", noteDescription);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteSubmit);

        navNotesTab.click();

    }

    public String getDisplayedNoteTitle() {
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedNoteTitle));
        return displayedNoteTitle.getText();
    }


    public String getDisplayedNoteDescription() {
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedNoteDescription));
        return displayedNoteDescription.getText();

    }

    public void editNote(String editedNoteTitle, String editedNoteDescription) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", editNoteButton);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + editedNoteTitle + "';", noteTitle);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].value='" + editedNoteDescription + "';", noteDescription);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", noteSubmit);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(navNotesTab));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", navNotesTab);
        webDriverWait.until(ExpectedConditions.visibilityOf(displayedNoteTitle));

    }

    public void deleteNote() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", deleteNoteButton);

        webDriverWait.until(ExpectedConditions.elementToBeClickable(navNotesTab)).click();
    }


    public boolean isNoteAvailable() {
        return webDriver.findElements(By.id("titleDescription")).size() > 0;
    }
}
