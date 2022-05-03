package pl.kozdrun.evolution.it;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import pl.kozdrun.evolution.EvolutionInterviewApplication;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/int-test/resources")
@CucumberContextConfiguration
@SpringBootTest(classes = EvolutionInterviewApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RunCucumberIT {
}