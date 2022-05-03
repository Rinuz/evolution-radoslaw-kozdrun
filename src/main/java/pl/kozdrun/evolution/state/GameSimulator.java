package pl.kozdrun.evolution.state;

import org.springframework.stereotype.Component;
import pl.kozdrun.evolution.state.model.ScoreType;

@Component
public class GameSimulator {

    public ScoreType playRound() {
        return generateScoreType();
    }

    private ScoreType generateScoreType() {
        double randomNumber = Math.random();
        if (randomNumber <= 0.3) {
            randomNumber = Math.random();
            if (randomNumber <= 0.33) {
                return ScoreType.WIN_SMALL;
            } else if (randomNumber <= 0.66 && randomNumber > 0.33) {
                return ScoreType.WIN_MEDIUM;
            } else {
                return ScoreType.WIN_BIG;
            }
        }
        return ScoreType.LOSE;
    }

    public boolean drawFreeRound() {
        double randomNumber = Math.random();
        if (randomNumber <= 0.1) {
            return true;
        }
        return false;
    }
}