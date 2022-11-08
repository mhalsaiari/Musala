package org.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.listener.SeleniumListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.utils.ConfigManager;
import org.utils.Helper;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Selenium webDriver manager
 *
 * @author Mohammed Alsaiari
 */
public class DriverManager {

    private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        return webDriverThreadLocal.get();
    }

    private static void setDriver(WebDriver driver) {
        webDriverThreadLocal.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = webDriverThreadLocal.get();
        if (driver != null) {
            driver.quit();
        }
    }

    public static void setBrowser(Browser browser) {
        setBrowser(browser, null);
    }

    public static void setBrowser(Browser browser, DesiredCapabilities userProvidedCapabilities) {
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            if (userProvidedCapabilities != null) {
                desiredCapabilities.merge(userProvidedCapabilities);
            } else {
                LOGGER.debug("User provided capability is null. Ignoring...");
            }
            LOGGER.info("Browser : {}", browser);
            EventFiringWebDriver eventFiringWebDriver;

            if (browser.toString().equalsIgnoreCase("chrome")) {

                String driver_path = Paths.get(System.getProperty("user.dir") + ConfigManager.getConfigProperty("chrome.driver")).toString();
                System.setProperty("webdriver.chrome.driver", driver_path);

                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_setting_values.notifications", 0);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("--safebrowsing-disable-download-protection");
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-breakpad");
                chromeOptions.addArguments("safebrowsing-disable-extension-blacklist");
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--allow-running-insecure-content");

                chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                chromeOptions.merge(desiredCapabilities);

                WebDriver driver = new ChromeDriver(chromeOptions);
                eventFiringWebDriver = new EventFiringWebDriver(driver);
                eventFiringWebDriver.register(new SeleniumListener());


            } else if (browser.toString().equalsIgnoreCase("firefox")) {

                String driver_path = Paths.get(System.getProperty("user.dir") + ConfigManager.getConfigProperty("firefox.driver")).toString();

                System.setProperty("webdriver.gecko.driver", driver_path);
                System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                Assert.assertNotNull(ConfigManager.getConfigProperty("firefox.browser.path"),
                        "Please set firefox browser installation path in config.properties");
                firefoxOptions.setBinary(ConfigManager.getConfigProperty("firefox.browser.path"));


                firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "text/xml," +
                        "text/csv,application/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml");
                firefoxOptions.addPreference("browser.download.panel.shown", false);
                firefoxOptions.merge(desiredCapabilities);
                WebDriver driver = new FirefoxDriver(firefoxOptions);
                eventFiringWebDriver = new EventFiringWebDriver(driver);
                eventFiringWebDriver.register(new SeleniumListener());
            } else {
                throw new IllegalArgumentException(
                        String.format("%s is invalid value. Enter valid browser value in config.properties", browser));
            }

            eventFiringWebDriver.manage().window().maximize();
            DriverManager.setDriver(eventFiringWebDriver);
        } catch (Exception e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }

    public static void openNewTab() {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.open()");
        Helper.log("Open New Tab");
    }

    public static void moveToTab() {
        String currentWindow = getDriver().getWindowHandle();
        Set<String> allWindows = getDriver().getWindowHandles();
        Iterator<String> i = allWindows.iterator();
        while (i.hasNext()) {
            String childwindow = i.next();
            if (!childwindow.equalsIgnoreCase(currentWindow)) {
                getDriver().switchTo().window(childwindow);

            }
        }
    }

}