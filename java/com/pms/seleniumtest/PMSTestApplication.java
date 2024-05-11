package com.pms.seleniumtest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;


@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PMSTestApplication {

    private static WebDriver driver;

    @Test
    @Order(1)
    public void testRegisterUser() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://localhost:3000/AddUser");

        WebElement userNameInput = driver.findElement(By.id("userName"));
        userNameInput.sendKeys("Test User");

        WebElement userMobnoInput = driver.findElement(By.id("userMobno"));
        userMobnoInput.sendKeys("1234567890");

        WebElement registerButton = driver.findElement(By.cssSelector("button[type='submit']"));
        registerButton.click();

        Thread.sleep(5000);

        driver.switchTo().alert().accept();

        assertThat(driver.getCurrentUrl()).contains("http://localhost:3000/CreatePetition");

        driver.quit();
    }

    @Test
    @Order(2)
    public void testRegisterComplaint() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://localhost:3000/CreatePetition");

        WebElement descriptionInput = driver.findElement(By.id("description"));
        descriptionInput.sendKeys("Testing Description done for test purposes");

        WebElement userDropdown = driver.findElement(By.id("userId"));
        userDropdown.click();

        Thread.sleep(2000);

        WebElement userOption = driver.findElement(By.xpath("//select[@id='userId']/option[2]"));
        userOption.click();

        WebElement registerButton = driver.findElement(By.cssSelector("button[type='submit']"));
        registerButton.click();

        Thread.sleep(5000);

        driver.switchTo().alert().accept();

        assertThat(driver.getCurrentUrl()).contains("http://localhost:3000/ViewPetitions");

        driver.quit();
    }

    @Test
    @Order(3)
    public void testUpdateComplaintStatus() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://localhost:3000/ViewPetitions");

        Thread.sleep(2000);

        WebElement statusDropdown = driver.findElement(By.xpath("//select[@class='form-select']"));
        statusDropdown.click();

        WebElement resolvedOption = driver.findElement(By.xpath("//select[@class='form-select']/option[text()='Resolved']"));
        resolvedOption.click();

        WebElement changeButton = driver.findElement(By.cssSelector("button.btn.btn-success"));
        changeButton.click();

        Thread.sleep(5000);

        driver.switchTo().alert().accept();

        assertThat(driver.getCurrentUrl()).contains("http://localhost:3000/ViewPetitions");

        driver.quit();
    }
}
