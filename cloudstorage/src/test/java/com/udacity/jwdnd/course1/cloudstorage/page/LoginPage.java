package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class LoginPage {

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "logout")
    private WebElement logout;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void performLogin(String username, String password) {
        this.username.clear();
        this.username.sendKeys(username);

        this.password.clear();
        this.password.sendKeys(password);

        this.submitButton.submit();
    }

    public boolean loggedOut() {
        return logout != null;
    }
}
