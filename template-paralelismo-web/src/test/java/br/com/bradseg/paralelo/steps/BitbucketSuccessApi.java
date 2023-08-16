package br.com.bradseg.paralelo.steps;

import br.com.bradseg.paralelo.pages.BitbucketPageAPI;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Platform;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import report.Report;

import java.io.File;
import java.io.IOException;

import static java.util.Objects.isNull;

public class BitbucketSuccessApi {

    private final Report report;
    private final WebDriver driver;
    private final BitbucketPageAPI bitbucketPage;
    private final TakesScreenshot takesScreenshot;

    @Autowired
    public BitbucketSuccessApi(
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

    @Given("que acesso o Bitbucket api")
    public void acessarBitbucket() {
        try {
            report.init(Platform.WEB);

            final String bitbucketUrl = "https://bitbucket.bradescoseguros.com.br:8443/projects/SHOLDVPS03";
            driver.navigate().to(bitbucketUrl);

        } catch (Exception e) {
            System.out.println("Erro ao executar (acessarBitbucket())");
            if (!isNull(driver)) {
                driver.quit();
            }
            throw new RuntimeException(e);
        }
    }

    @When("pesquiso pelo repositorio api")
    public void pesquisoRepositorio() {
        try {
            bitbucketPage.pesquisarRepositorio("api");
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar repositório");
            if (!isNull(driver)) {
                driver.quit();
            }
            throw new RuntimeException(e);
        }
    }

    @Then("deve aparecer o repositorio pesquisado api")
    public void verificoRepositorio() throws InterruptedException {
        try {
            bitbucketPage.verificarResultadoPesquisa();
        } catch (Exception e) {
            System.out.println("Erro ao verificar repositório");
            if (!isNull(driver)) {
                driver.quit();
            }
            throw new RuntimeException(e);
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) throws IOException {
        if (!isNull(driver)) {
            File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
            report.add(scenario.getName(), FileUtils.readFileToByteArray(src));
        }
    }
}