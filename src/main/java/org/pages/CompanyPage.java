package org.pages;

import org.base.Actions;
import org.base.DriverManager;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.utils.ConfigManager;

public class CompanyPage {


    public static final By COMPANY_BUTTON = By.xpath("//*[@id=\"menu-main-nav-1\"]/li[1]/a");
    public static final By LEADERSHIP_SECTION = By.xpath("//*[@id=\"content\"]/div[2]/section[3]/div/h2");
    public static final By FACEBOOK_URL = By.xpath("/html/body/footer/div/div/a[4]/span");
    public static final By ACCEPT_COOKIES = By.xpath("//*[@id=\"wt-cli-accept-all-btn\"]");


    public void ClickCompanyAndValidateLink() {
        Actions.openUrl(ConfigManager.getConfigProperty("url"));
        Actions.sleep(10);
        Actions.waitUntilClickable(COMPANY_BUTTON);
        Actions.click(COMPANY_BUTTON, "Clicked Company button");
        Actions.sleep(5);
        String url = DriverManager.getDriver().getCurrentUrl();

        // validate Leadership section // isElementVisible
        boolean LeaderShipSection = Actions.checkIfWebElementExists(LEADERSHIP_SECTION);

        Actions.click(ACCEPT_COOKIES, "Clicked Accept Cookies");
        Actions.sleep(5);
        Actions.click(FACEBOOK_URL);

        DriverManager.moveToTab();
        Actions.sleep(5);


        Assert.assertEquals(url, "https://www.musala.com/company/");
        Assert.assertTrue(LeaderShipSection, "LeaderShip Section Visible");
        Assert.assertEquals(DriverManager.getDriver().getCurrentUrl(), "https://www.facebook.com/MusalaSoft?fref=ts");

    }

}
