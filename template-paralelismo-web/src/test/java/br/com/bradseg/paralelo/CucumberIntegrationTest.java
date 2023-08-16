package br.com.bradseg.paralelo;

import io.cucumber.junit.platform.engine.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.context.annotation.Import;

@Cucumber
@CucumberContextConfiguration
@Import(TestConfig.class)
public class CucumberIntegrationTest {
}
