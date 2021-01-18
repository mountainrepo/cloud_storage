package com.udacity.jwdnd.course1.cloudstorage.page;

import com.udacity.jwdnd.course1.cloudstorage.result.Credential;
import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.util.List;

public class CredentialsTab extends HomePage {

    @FindBy(id = "addCredential")
    private WebElement addButton;

    @FindBy(id = "credentialTable")
    private WebElement table;

    @FindBy(id = "credentialModal")
    private WebElement dialog;

    @FindBy(id = "credentialUrl")
    private WebElement url;

    @FindBy(id = "credentialUsername")
    private WebElement username;

    @FindBy(id = "credentialPassword")
    private WebElement password;

    @FindBy(id = "closeCredentialModal")
    private WebElement closeButton;

    @FindBy(id = "saveCredentialModal")
    private WebElement saveButton;

    public CredentialsTab(WebDriver driver) {
        super(driver);
    }

    public boolean clickAddNewCredential() {
        this.addButton.click();

        return this.dialog != null;
    }

    public void performAddNewCredential(String url, String username, String password) {
        this.url.clear();
        this.url.sendKeys(url);

        this.username.clear();
        this.username.sendKeys(username);

        this.password.clear();
        this.password.sendKeys(password);

        this.saveButton.click();
    }

    public String performEdit(String rowNumber, String username, String password) throws InterruptedException {
        WebElement tableRow = this.table.findElement(By.id(rowNumber));
        String credentialId = tableRow.findElement(By.name("credentialId")).getAttribute("value");
        tableRow.findElement(By.name("edit")).click();

        if(this.dialog == null) {
            return null;
        }

        Thread.sleep(1000);

        if(username != null) {
            this.username.clear();
            this.username.sendKeys(username);
        }

        if(password != null) {
            this.password.clear();
            this.password.sendKeys(password);
        }

        this.saveButton.click();

        return credentialId;
    }

    public Credential getCredentialByUrl(String url) {
        List<WebElement> tableRowList = this.table.findElements(By.tagName("tr"));

        for(WebElement tableRow : tableRowList) {
            List<WebElement> urlElements = tableRow.findElements(By.name("url"));

            if(urlElements.size() == 0) {
                continue;
            }

            if(urlElements.get(0).getText().equals(url)) {
                String username = tableRow.findElement(By.name("username")).getText();
                String password = tableRow.findElement(By.name("password")).getText();

                return new Credential(username, password);
            }
        }

        return null;
    }

    public boolean doesCredentialExists(int id) {
        String credId = "credential" + id;

        List<WebElement> elementList = this.table.findElements(By.id(credId));

        return elementList.size() == 1;
    }

    public Credential getCredentialById(int id) {
        List<WebElement> tableRowList = this.table.findElements(By.tagName("tr"));

        for(WebElement tableRow : tableRowList) {
            List<WebElement> credentialId = tableRow.findElements(By.name("credentialId"));

            if(credentialId.size() == 0) {
                continue;
            }

            String username = tableRow.findElement(By.name("username")).getText();
            String password = tableRow.findElement(By.name("password")).getText();

            return new Credential(username, password);
        }

        return null;
    }

    public Credential getCredentialByRowNumber(int rowNumber) {
        WebElement tableRow = this.table.findElement(By.tagName("tr")).findElement(By.id(Integer.toString(rowNumber)));

        if(tableRow == null) {
            return null;
        }
        else {
            String username = tableRow.findElement(By.name("username")).getText();
            String password = tableRow.findElement(By.name("password")).getText();
            return new Credential(username, password);
        }
    }

    public String getUrlByRowNumber(int rowNumber) {
        WebElement tableRow = this.table.findElement(By.id(Integer.toString(rowNumber)));

        if(tableRow == null) {
            return null;
        }
        else {
            return tableRow.findElement(By.name("url")).getText();
        }
    }

    public int performDelete(String rowNumber) {
        WebElement tableRow = this.table.findElement(By.id(rowNumber));
        String credentialId = tableRow.findElement(By.name("credentialId")).getAttribute("value");
        tableRow.findElement(By.name("delete")).click();

        return Integer.parseInt(credentialId);
    }

    public boolean findCredentialById(int id) {
        WebElement idElement = this.table.findElement(By.id("credential" + id));

        return idElement != null;
    }
}
