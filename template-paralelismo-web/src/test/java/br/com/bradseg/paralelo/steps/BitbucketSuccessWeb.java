package br.com.bradseg.paralelo.steps;

import br.com.bradseg.paralelo.pages.BitbucketPageWeb;
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

public class BitbucketSuccessWeb {

    private final Report report;
    private final WebDriver driver;
    private final BitbucketPageWeb bitbucketPage;
    private final TakesScreenshot takesScreenshot;

    @Autowired
    public BitbucketSuccessWeb(
            Report report,
            @Qualifier("webDriver") WebDriver driver,
            BitbucketPageWeb bitbucketPage,
            TakesScreenshot takesScreenshot
    ) {
        this.report = report;
        this.driver = driver;
        this.bitbucketPage = bitbucketPage;
        this.takesScreenshot = takesScreenshot;
    }

    @Given("que acesso o Bitbucket web")
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

    @When("pesquiso pelo repositorio web")
    public void pesquisoRepositorio() {
        try {
            bitbucketPage.pesquisarRepositorio("web");
        } catch (Exception e) {
            System.out.println("Erro ao pesquisar repositório");
            if (!isNull(driver)) {
                driver.quit();
            }
            throw new RuntimeException(e);
        }
    }

    @Then("deve aparecer o repositorio pesquisado web")
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