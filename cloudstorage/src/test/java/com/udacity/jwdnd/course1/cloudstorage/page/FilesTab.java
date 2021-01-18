package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.util.List;

public class FilesTab extends HomePage {

    @FindBy(id = "fileUpload")
    private WebElement fileUpload;

    @FindBy(id = "uploadButton")
    private WebElement uploadButton;

    @FindBy(id = "fileTable")
    private WebElement fileTable;

    public FilesTab(WebDriver driver) {
        super(driver);
    }

    public void performUpload(String filePath) {
        this.fileUpload.sendKeys(filePath);

        this.uploadButton.submit();
    }

    public int performDelete(int rowNumber) {
        WebElement tableRow = this.fileTable.findElement(By.id(Integer.toString(rowNumber)));

        int id = Integer.parseInt(tableRow.findElement(By.name("fileId")).getAttribute("value"));

        tableRow.findElement(By.name("delete")).click();

        return id;
    }

    public boolean isFileDisplayedById(int id) {
        List<WebElement> elements = this.fileTable.findElements(By.id(Integer.toString(id)));

        return  elements.size() == 1;
    }

    public boolean isFileDisplayed(String fileName) {
        List<WebElement> fileRow = this.fileTable.findElements(By.id(fileName));

        return fileRow.size() == 1;
    }
}
