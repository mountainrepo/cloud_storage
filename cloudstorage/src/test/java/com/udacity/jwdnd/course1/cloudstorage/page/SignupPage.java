package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class SignupPage {

    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement username;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submit")
    private WebElement submitButton;

    @FindBy(id = "signupSuccess")
    private WebElement successMessage;

    @FindBy(id = "signupError")
    private WebElement errorMessage;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void performSignup(String firstName, String lastName, String username, String password) {
        this.firstName.clear();
        this.firstName.sendKeys(firstName);

        this.lastName.clear();
        this.lastName.sendKeys(lastName);

        this.username.clear();
        this.username.sendKeys(username);

        this.password.clear();
        this.password.sendKeys(password);

        this.submitButton.submit();
    }

    public boolean isSignupSuccessful() {
        return this.successMessage != null;
    }
}
