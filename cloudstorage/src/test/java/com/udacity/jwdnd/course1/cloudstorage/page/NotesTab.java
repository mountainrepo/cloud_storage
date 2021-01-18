package com.udacity.jwdnd.course1.cloudstorage.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

import java.util.List;

public class NotesTab extends HomePage {

    @FindBy(id = "addNote")
    private WebElement addButton;

    @FindBy(id = "noteTable")
    private WebElement table;

    @FindBy(id = "noteModal")
    private WebElement dialog;

    @FindBy(id = "noteTitle")
    private WebElement title;

    @FindBy(id = "noteDescription")
    private WebElement description;

    @FindBy(id = "saveNoteModal")
    private WebElement saveButton;

    public NotesTab(WebDriver driver) {
        super(driver);
    }

    public boolean clickAddNewNote() {
        this.addButton.click();

        return this.dialog != null;
    }

    public void performAdd(String title, String description) {
        this.title.clear();
        this.title.sendKeys(title);

        this.description.clear();
        this.description.sendKeys(description);

        this.saveButton.click();
    }

    public boolean isTitleDisplayed(String title) {
        WebElement titleElement = this.table.findElement(By.id(title));

        return titleElement != null;
    }

    public boolean isNoteDisplayed(int noteId) {
        List<WebElement> noteRow = this.table.findElements(By.id("note" + noteId));

        return noteRow.size() == 1;
    }

    public String getDescription(String title) {
        List<WebElement> elementList = this.table.findElements(By.tagName("tr"));

        WebElement descriptionElement = elementList.get(1).findElement(By.name("description"));

        return descriptionElement.getText();
    }

    public int performDelete(String rowNumber) {
        WebElement tableRow = this.table.findElement(By.id(rowNumber));

        String noteId = tableRow.findElement(By.name("noteId")).getAttribute("value");

        tableRow.findElement(By.name("delete")).click();

        return Integer.parseInt(noteId);
    }

    public boolean performEdit(String rowNumber, String title, String description) throws InterruptedException {
        WebElement tableRow = this.table.findElement(By.id(rowNumber));

        String noteId = tableRow.findElement(By.name("noteId")).getAttribute("value");

        tableRow.findElement(By.name("edit")).click();

        Thread.sleep(1000);

        if(dialog == null) {
            return false;
        }

        if(title != null) {
            this.title.clear();
            this.title.sendKeys(title);
        }

        if(description != null) {
            this.description.clear();
            this.description.sendKeys(description);
        }

        this.saveButton.click();

        return true;
    }
}
