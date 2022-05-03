package pl.kozdrun.evolution.state;

import org.springframework.stereotype.Component;
import pl.kozdrun.evolution.rest.model.request.PlayRoundRequest;
import pl.kozdrun.evolution.state.model.PlayMode;
import pl.kozdrun.evolution.state.model.ScoreType;

import java.math.BigDecimal;

@Component
public class BalanceCalculator {

    public BigDecimal calculateRoundScoreAmount(PlayRoundRequest playRoundRequest, ScoreType scoreType, boolean freeRound) {
        if (PlayMode.FREE == playRoundRequest.getPlayMode()) {
            return BigDecimal.ZERO;
        }
        if (freeRound && ScoreType.LOSE == scoreType) {
            return BigDecimal.ZERO;
        }

        if (ScoreType.WIN_SMALL == scoreType) {
            return BigDecimal.valueOf(3).multiply(playRoundRequest.getBetAmount());
        } else if (ScoreType.WIN_MEDIUM == scoreType) {
            return BigDecimal.valueOf(10).multiply(playRoundRequest.getBetAmount());
        } else if (ScoreType.WIN_BIG == scoreType) {
            return BigDecimal.valueOf(50).multiply(playRoundRequest.getBetAmount());
        } else {
            return playRoundRequest.getBetAmount().negate();
        }
    }
}