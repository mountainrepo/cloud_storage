package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class HomePage {

    @FindBy(id = "logout")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "nav-files-tab")
    private WebElement filesTab;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void performLogout() {
        logoutButton.submit();
    }

    public void clickNotesTab() {
        notesTab.click();
    }

    public void clickCredentialsTab() {
        credentialsTab.click();
    }

    public void clickFilesTab() {
        filesTab.click();
    }
}
