package org;

import org.base.BaseTest;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.pages.Careers;
import org.testng.annotations.Test;

public class CareersPageTests extends BaseTest {



    @Test(testName = "Validate Careers Page")
    public void ValidateCareersPage(){

        Careers careers = new Careers();
        careers.navigateToCareerPage();
        careers.navigateToJoinUsPage();
        careers.validateJoinUsUrl();

        careers.selectLocation("Anywhere");
        careers.selectCareer();

        careers.verifySections();
        careers.verifyApplyButtonPresentandClick();
        careers.fillApplyInformation();
        careers.clickSendAndVerifyMessage();
    }
    @Test(testName = "Get All available job of city")
    public void getAllavailableJobs(){
        Careers careers = new Careers();
        careers.navigateToCareerPage();
        careers.navigateToJoinUsPage();
        careers.selectLocation("Sofia");
        careers.getAllavailableJobs("Sofia");
        careers.selectLocation("Skopje");
        careers.getAllavailableJobs("Skopje");
    }
    @Override
    public DesiredCapabilities addCapabilities() {
        return null;
    }
}
