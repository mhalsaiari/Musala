package org.pages;

import org.base.Actions;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.utils.ConfigManager;

import java.util.Map;

public class HomePage {


    public static final By CONTACT_US_BUTTON = By.xpath("/html/body/main/section[2]/div/div/div/a[1]/button");
    public static final By ACCEPT_COOKIES = By.xpath("//*[@id=\"wt-cli-accept-all-btn\"]");

    public static final By NAME_INPUT = By.xpath("//*[@id=\"cf-1\"]");
    public static final By EMAIL_INPUT = By.xpath("//*[@id=\"cf-2\"]");
    public static final By MOBILE_INPUT = By.xpath("//*[@id=\"cf-3\"]");
    public static final By SUBJECT_INPUT = By.xpath("//*[@id=\"cf-4\"]");
    public static final By YOURMESSAGE_INPUT = By.xpath("//*[@id=\"cf-5\"]");

    public static final By SEND_BUTTON = By.xpath("//*[@id=\"wpcf7-f875-o1\"]/form/div[3]/p/input");
    public static final By ERROR_MESSAGE = By.xpath("//*[@id=\"wpcf7-f875-o1\"]/form/p[2]/span/span");


    public void ClickContactUsButton(Map<String, String> data) {

        Actions.openUrl(ConfigManager.getConfigProperty("url"));

        Actions.sleep(10);


        Actions.click(ACCEPT_COOKIES, "Clicked Accept Cookies");

        Actions.click(CONTACT_US_BUTTON, "click contact us button");
        Actions.sleep(5);


    }

    public void fillContactUsDataAndSubmit(Map<String, String> data) {
        Actions.enterText(NAME_INPUT, data.get("Name"), "Entered Name value");
        Actions.enterText(EMAIL_INPUT, data.get("Email"), "Entered Email value");
        Actions.enterText(MOBILE_INPUT, data.get("Mobile"), "Entered Mobile value");
        Actions.enterText(SUBJECT_INPUT, data.get("Subject"), "Entered Subject value");
        Actions.enterText(YOURMESSAGE_INPUT, data.get("YourMessage"), "Entered Message value");

        Actions.sleep(5);
        Actions.click(SEND_BUTTON, "Clicked Send button");
        Assert.assertEquals(Actions.getText(ERROR_MESSAGE), "The e-mail address entered is invalid.");
    }

}
