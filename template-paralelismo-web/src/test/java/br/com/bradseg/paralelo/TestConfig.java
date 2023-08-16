package br.com.bradseg.paralelo;

import br.com.bradseg.paralelo.pages.BitbucketPageAPI;
import br.com.bradseg.paralelo.pages.BitbucketPageMobile;
import br.com.bradseg.paralelo.pages.BitbucketPageWeb;
import main.Variaveis;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.support.SimpleThreadScope;
import report.Report;

@Configuration
public class TestConfig {

    @Bean
    public Report report() {
        return new Report();
    }

    @Bean("webDriver")
    @Scope(scopeName = "cucumber-glue", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ChromeDriver webDriver() {

        final String driverPath = getDriverPathByOS("chromedriver");
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeOptions options = new ChromeOptions();

        options.addArguments("start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.setCapability("acceptInsecureCerts", true);
        options.addArguments("--ignore-certificate-errors");
        options.setCapability("networkConnectionEnabled", true);
        options.setCapability("networkConnectionTimeout", 5000); // 5 segundos

        return new ChromeDriver(options);
    }

    @Bean
    public BitbucketPageAPI bitbucketPageAPI(WebDriver webDriver) {
        return new BitbucketPageAPI(webDriver);
    }

    @Bean
    public BitbucketPageMobile bitbucketPageMobile(WebDriver webDriver) {
        return new BitbucketPageMobile(webDriver);
    }

    @Bean
    public BitbucketPageWeb bitbucketPageWeb(WebDriver webDriver) {
        return new BitbucketPageWeb(webDriver);
    }

    @Bean("takesScreenshot")
    public TakesScreenshot takesScreenshot(WebDriver webDriver) {
        return (TakesScreenshot) webDriver;
    }

    @Bean
    public static BeanFactoryPostProcessor threadScopeBeanFactoryPostProcessor() {
        return beanFactory -> beanFactory.registerScope("thread", new SimpleThreadScope());
    }

    private String getDriverPathByOS(String driverName){
        String driverPath = Variaveis.get().asString("driverPath");
        String osName = Variaveis.get().asString("os.name", "windows").toLowerCase();

        if(osName.contains("windows")) {
            return driverPath + driverName + ".exe";
        }
        else return driverPath + driverName; //Linux
    }
}