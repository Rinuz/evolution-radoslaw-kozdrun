package pl.kozdrun.evolution.rest.validate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import pl.kozdrun.evolution.config.GameConfig;
import pl.kozdrun.evolution.exception.RestException;
import pl.kozdrun.evolution.state.GameStateManager;
import pl.kozdrun.evolution.util.NumberUtils;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PlayersControllerValidator {

    private final GameConfig gameConfig;
    private final GameStateManager gameStateManager;

    public void validatePlayerId(String playerId) {
        if (!gameStateManager.userExists(playerId)) {
            throw new RestException("Player with id " + playerId + " does not exist", HttpStatus.BAD_REQUEST);
        }
    }

    public void validatePlayerIdAndGameId(String playerId, String gameId) {
        validatePlayerId(playerId);
        if (!gameStateManager.gameExists(playerId, gameId)) {
            throw new RestException("Game with id " + gameId + " does not exist", HttpStatus.BAD_REQUEST);
        }
    }


    public void validateBalance(BigDecimal balance, BigDecimal betAmount) {
        if (NumberUtils.lowerThanZero(balance.subtract(betAmount))) {
            throw new RestException("Insufficient funds on account. Current balance is " + balance, HttpStatus.BAD_REQUEST);
        }
    }

    public void validateBetAmountRange(BigDecimal betAmount) {
        BigDecimal min = gameConfig.getSettings().getBetMinAmount();
        BigDecimal max = gameConfig.getSettings().getBetMaxAmount();
        if (betAmount == null || !NumberUtils.isBetween(betAmount, min, max)) {
            throw new RestException("Bet amount " + betAmount + " should be in range " + min + "-" + max, HttpStatus.BAD_REQUEST);
        }
    }

    public void validateRoundNumber(String playerId, String gameId, int roundNumber) {
        if (roundNumber <= 0 && roundNumber >= gameStateManager.getGame(playerId, gameId).getRounds().size()) {
            throw new RestException("Round number " + roundNumber + " cannot be found for plyerId=" + playerId + " and gameId=" + gameId, HttpStatus.BAD_REQUEST);
        }
    }
}