package pl.kozdrun.evolution.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@ConfigurationProperties("game")
@Getter
@Setter
public class GameConfig {

    private GameSettings settings;

    @Getter
    @Setter
    public static class GameSettings {

        private BigDecimal initialBalance;
        private BigDecimal betMinAmount;
        private BigDecimal betMaxAmount;
    }
}
