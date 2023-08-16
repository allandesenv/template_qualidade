package br.com.bradseg.paralelo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class BitbucketPageMobile {
    private final WebDriver driver;

    @FindBy(css = "input.paged-table-filter-input[placeholder='Filter repositories by name']")
    public static WebElement campoPesquisarRepositorio;

    @FindBy(className = "paged-table")
    public static WebElement tabelaResultados;

    public BitbucketPageMobile(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(driver, this);
    }

    public List<WebElement> getLinhasResultadosPesquisa() {
        return tabelaResultados.findElements(By.tagName("tr"));
    }

    public void pesquisarRepositorio(String repo){
        campoPesquisarRepositorio.sendKeys(repo);
     }

    public void verificarResultadoPesquisa() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.visibilityOf(getLinhasResultadosPesquisa().get(0)));
       assert (getLinhasResultadosPesquisa().get(0).isDisplayed());
    }

}
