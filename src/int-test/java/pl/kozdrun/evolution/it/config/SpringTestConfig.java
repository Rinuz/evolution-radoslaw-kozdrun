package pl.kozdrun.evolution.it.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;
import pl.kozdrun.evolution.state.GameSimulator;

@Configuration
public class SpringTestConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Primary
    @Bean
    public GameSimulator getGameSimulator() {
        return Mockito.mock(GameSimulator.class);
    }
}