package org.pages;

import org.base.Actions;
import org.base.BaseTest;
import org.base.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.utils.ConfigManager;
import org.utils.Helper;

import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class Careers extends BaseTest {

    public static final By CAREER_BUTTON = By.xpath("//*[@id=\"menu-main-nav-1\"]/li[5]");
    public static final By Check_open_positions = By.xpath("//*[@id=\"content\"]/div[1]/div/div[1]/div/section/div/a");
    public static final By LOCATION_SELECT = By.xpath("//*[@id=\"get_location\"]");

    public static final By JOB = By.xpath("//h2[text()='Senior Java Developer']"); //By.xpath("//*[text()='Senior Java Developer']\"");

    public static final By General_description = By.xpath("//*[@id=\"post-5397\"]/div/div[2]/div[1]/div[1]/div[1]/div[2]/h2");
    public static final By Requirements = By.xpath("//*[@id=\"post-5397\"]/div/div[2]/div[1]/div[1]/div[2]/div[2]/h2");
    public static final By Responsibilities = By.xpath("//*[@id=\"post-5397\"]/div/div[2]/div[1]/div[2]/div[1]/div[2]/h2");
    public static final By What_we_offer = By.xpath("//*[@id=\"post-5397\"]/div/div[2]/div[1]/div[2]/div[2]/div[2]/h2");
    public static final By APPLY_BUTTON = By.xpath("//*[@id=\"post-5397\"]/div/div[2]/div[2]/a/input");

    public static final By ACCEPT_COOKIES = By.xpath("//*[@id=\"wt-cli-accept-all-btn\"]");

    public static final By Name = By.xpath("//*[@id=\"cf-1\"]");
    public static final By Email = By.xpath("//*[@id=\"cf-2\"]");
    public static final By Mobile = By.xpath("//*[@id=\"cf-3\"]");
    public static final By Cv = By.xpath("//*[@id=\"uploadtextfield\"]");


    public static final By AGREE_CHECK = By.xpath("//*[@id=\"adConsentChx\"]");
    public static final By SEND_BUTTON = By.xpath("//*[@id=\"wpcf7-f880-o1\"]/form/div[4]/p/input");
    public static final By CLOSE_BUTTON = By.xpath("//*[@id=\"wpcf7-f880-o1\"]/form/div[5]/div/button");

    public static final By Email_Error_Message = By.xpath("//*[@id=\"wpcf7-f880-o1\"]/form/p[3]/span/span[1]");
    public static final By Mobile_Error_Message = By.xpath("//*[@id=\"wpcf7-f880-o1\"]/form/p[4]/span/span");

    public static final By PARENT_CLASS = By.xpath("//*[@id=\"content\"]/section/div[2]");

    public void navigateToCareerPage() {
        Actions.openUrl(ConfigManager.getConfigProperty("url"));
        Actions.sleep(10);
        Actions.waitUntilClickable(CAREER_BUTTON);
        Actions.click(CAREER_BUTTON, "Clicked Careers button");
        Actions.sleep(2);
    }

    public void navigateToJoinUsPage() {

        Actions.click(Check_open_positions, "Clicked Check open positions button");
        Actions.sleep(2);

    }

    public void validateJoinUsUrl() {
        String joinUsUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertEquals(joinUsUrl, "https://www.musala.com/careers/join-us/");
    }

    public void selectLocation(String location) {
        Actions.selectFromListByVisibleText(LOCATION_SELECT, location, "Selected Anywhere option");
        Actions.sleep(2);
    }

    public void selectCareer() {
        Actions.click(ACCEPT_COOKIES, "Clicked Accept Cookies");
        Actions.click(JOB);
    }

    public void verifySections() {

        Assert.assertTrue(Actions.isElementVisible(General_description, "General Description is visible"));
        Assert.assertTrue(Actions.isElementVisible(Requirements, "Requirements is visible"));
        Assert.assertTrue(Actions.isElementVisible(Responsibilities, "Responsibilities is visible"));
        Assert.assertTrue(Actions.isElementVisible(What_we_offer, "What we offer is visible"));
    }

    public void verifyApplyButtonPresentandClick() {

        Actions.isElementVisible(APPLY_BUTTON, "Apply button is visible");

        // click ACCEPT COOKIES


        Actions.click(APPLY_BUTTON, "clicked Apply button");
    }

    public void fillApplyInformation() {
        Actions.enterText(Name, "Mohammed", "filled Name input");
        Actions.enterText(Email, "m@m", "filled Email input");
        Actions.enterText(Mobile, "aa", "filled Mobile input");

        String cvPath = Paths.get(System.getProperty("user.dir") + "/src/main/resources/cv-example.pdf").toString();
        Actions.enterText(Cv, cvPath, "filled CV input");

        Actions.click(AGREE_CHECK, "clicked agree");
        Actions.sleep(5);
    }

    public void clickSendAndVerifyMessage() {
        Actions.click(SEND_BUTTON, "Clicked Send button");
        Actions.sleep(2);
        Actions.click(CLOSE_BUTTON, "Clicked close button");

        Actions.sleep(2);

        String emailError = Actions.getText(Email_Error_Message);
        String mobileError = Actions.getText(Mobile_Error_Message);

        Assert.assertEquals(emailError, "The e-mail address entered is invalid.");
        Assert.assertEquals(mobileError, "The telephone number is invalid.");
    }

    public void getAllavailableJobs(String city) {

        WebElement parentElement = Actions.find(PARENT_CLASS);

        List<WebElement> jobTitle = parentElement.findElements(By.xpath(".//*[@class='card-jobsHot__title']"));
        List<WebElement> jobLink = parentElement.findElements(By.xpath(".//*[@class='card-jobsHot__link']"));

        Iterator<WebElement> i1 = jobTitle.iterator();
        Iterator<WebElement> i2 = jobLink.iterator();

        Helper.log(city);
        while (i1.hasNext() && i2.hasNext()) {

            Helper.log("Position: " + i1.next().getText());
            Helper.log("More info: " + i2.next().getAttribute("href"));
        }
    }

    @Override
    public DesiredCapabilities addCapabilities() {
        return null;
    }
}
