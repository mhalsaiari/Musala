package org;

import org.base.BaseTest;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.pages.CompanyPage;
import org.testng.annotations.Test;

import java.util.Map;

public class CompanyPageTests extends BaseTest {


    @Test(testName = "Validate links of Company page")
    public void ValidateCompanyPage(){

        CompanyPage companyPage = new CompanyPage();
        companyPage.ClickCompanyAndValidateLink();


    }



    @Override
    public DesiredCapabilities addCapabilities() {
        return null;
    }
}
