package br.com.bradseg.paralelo.steps;

import br.com.bradseg.paralelo.pages.BitbucketPageAPI;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import report.Report;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class BaseSteps {

    private final Report report;
    private final WebDriver driver;
    private final BitbucketPageAPI bitbucketPage;
    private final TakesScreenshot takesScreenshot;

    @Autowired
    public BaseSteps(
            Report report,
            @Qualifier("webDriver") WebDriver driver,
            BitbucketPageAPI bitbucketPage,
            TakesScreenshot takesScreenshot
    ) {
        this.report = report;
        this.driver = driver;
        this.bitbucketPage = bitbucketPage;
        this.takesScreenshot = takesScreenshot;
    }

   @After
    public void close(Scenario scenario) {
        String scenarioName = scenario.getStatus() + "_" + LocalDateTime.now() + ".pdf";
        report.generatePDF(scenario.getStatus().name(), scenario.getName(), true);

        if (!isNull(driver)) {
            driver.quit();
        }
    }
}