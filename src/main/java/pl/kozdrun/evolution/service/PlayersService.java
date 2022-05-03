package pl.kozdrun.evolution.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import pl.kozdrun.evolution.rest.model.request.PlayRoundRequest;
import pl.kozdrun.evolution.state.BalanceCalculator;
import pl.kozdrun.evolution.state.GameSimulator;
import pl.kozdrun.evolution.state.GameStateManager;
import pl.kozdrun.evolution.state.model.GameState;
import pl.kozdrun.evolution.state.model.PlayerState;
import pl.kozdrun.evolution.state.model.Round;
import pl.kozdrun.evolution.state.model.ScoreType;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PlayersService {

    private final GameStateManager gameStateManager;
    private final GameSimulator gameSimulator;
    private final BalanceCalculator balanceCalculator;

    public String newPlayer() {
        String playerId = RandomStringUtils.randomAlphanumeric(10);
        gameStateManager.addPlayer(playerId);
        return playerId;
    }

    public GameState newGame(String playerId) {
        GameState newGame = new GameState();
        newGame.setId(RandomStringUtils.randomAlphanumeric(10));
        newGame.setBalance(BigDecimal.valueOf(5000));
        gameStateManager.addGame(playerId, newGame);
        return newGame;
    }

    public Round playRound(String playerId, String gameId, PlayRoundRequest playRoundRequest) {
        ScoreType scoreType = gameSimulator.playRound();
        boolean currentRoundFree = gameStateManager.isNextRoundFree(playerId, gameId);
        BigDecimal scoreAmount = balanceCalculator.calculateRoundScoreAmount(playRoundRequest, scoreType, currentRoundFree);

        Round round = new Round();
        round.setBetAmount(playRoundRequest.getBetAmount());
        round.setPlayMode(playRoundRequest.getPlayMode());
        round.setScoreType(scoreType);
        round.setFreeRound(currentRoundFree);
        round.setScoreAmount(scoreAmount);
        gameStateManager.addRoundAndRecalculateBalance(playerId, gameId, round);

        boolean nextFreeRound = gameSimulator.drawFreeRound();
        gameStateManager.setNextRoundFree(playerId, gameId, nextFreeRound);

        return round;
    }

    public BigDecimal getGameBalance(String playerId, String gameId) {
        return getGame(playerId, gameId).getBalance();
    }

    public GameState getGame(String playerId, String gameId) {
        return gameStateManager.getGame(playerId, gameId);
    }

    public PlayerState getPlayer(String playerId) {
        return gameStateManager.getPlayer(playerId);
    }

    public Round getRound(String playerId, String gameId, int roundNumber) {
        return getGame(playerId, gameId).getRounds().get(roundNumber - 1);
    }
}