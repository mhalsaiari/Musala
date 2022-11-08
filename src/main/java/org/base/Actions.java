package org.base;


import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.report.ExtentTestManager;
import org.utils.ConfigManager;
import org.utils.Helper;

import java.util.Objects;

public class Actions {


    private static final Logger LOGGER = LogManager.getLogger(Actions.class);

    public static void takeScreenshot() {
        String base64Screenshot = ((TakesScreenshot) Objects.requireNonNull(DriverManager.getDriver())).getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest()
                .info("Screenshot", MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
    }

    public static void sleep(int sleepInSeconds) {
        try {
            LOGGER.info("Waiting for {} Seconds", sleepInSeconds);
            Thread.sleep(sleepInSeconds * 1000L);
        } catch (Exception e) {
            //
        }
    }

    public static void openUrl(String url) {
        openUrl(url, "Opened url : " + url);
    }

    public static void openUrl(String url, String message) {
        DriverManager.getDriver().get(url);
        Helper.log(message);
    }


    public static WebElement find(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),
                Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
        return wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static WebElement find(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),
                Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
        return wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static void click(By by) {
        click(by, "Clicked : " + by.toString());
    }

    public static void click(By by, String message) {
        click(find(by), message);
    }

    public static void moveTo(By by) {

    }

    public static void click(WebElement webElement, String message) {
        webElement.click();
        Helper.log(message);
    }


    public static void waitUntilClickable(By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),
                Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitUntilClickable(WebElement webElement) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),
                Integer.parseInt(ConfigManager.getConfigProperty("explicit.wait.time")));
        wait.until(ExpectedConditions.elementToBeClickable(webElement));
    }

    public static boolean checkIfWebElementExists(By by) {
        try {
            if (DriverManager.getDriver().findElement(by).isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void enterText(By by, String value, String message) {
        enterText(find(by), value, message);
    }
    public static void enterText(WebElement webElement, String value, String message) {
        webElement.sendKeys(value);
        Helper.log(message);
    }
    public static String getText(By by) {
        return find(by).getText();
    }

    public static String getText(WebElement webElement) {
        return webElement.getText();
    }

    public static String getLink(WebElement webElement) {
        return webElement.getAttribute("href");
    }

    public static String getAttribute(By by) {
        return find(by).getAttribute("value");
    }

    public static boolean isElementVisible(By by) {

        return isElementVisible(by, "Check if element: " + by.toString() + " is exist");
    }

    public static boolean isElementVisible(By by, String message) {

        return isElementVisible(find(by), message);
    }

    public static boolean isElementVisible(WebElement webElement, String message) {

        return webElement.isDisplayed();
    }

    public static void selectFromListByVisibleText(By by, String value, String message) {
        selectFromListByVisibleText(find(by), value, message);
    }

    public static void selectFromListByVisibleText(WebElement webElement, String value, String message) {
        final Select select = new Select(webElement);
        select.selectByVisibleText(value);
        LOGGER.info(message);
    }

    public static void ScrollDownWebPage() {
        WebDriver driver = DriverManager.getDriver();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,500)");
    }

}
