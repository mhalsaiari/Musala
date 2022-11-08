package org;

import org.base.BaseTest;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.pages.HomePage;
import org.testng.annotations.Test;

import java.util.Map;

public class HomePageTests extends BaseTest{


    @Test(dataProvider = "testDataProvider")
    public void validateEmail(Map<String,String> data){

        HomePage homePage = new HomePage();
        homePage.ClickContactUsButton(data);
        homePage.fillContactUsDataAndSubmit(data);

    }


    @Override
    public DesiredCapabilities addCapabilities() {
        return null;
    }
}
