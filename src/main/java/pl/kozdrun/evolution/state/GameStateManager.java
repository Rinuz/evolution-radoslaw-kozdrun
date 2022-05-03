package pl.kozdrun.evolution.state;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.kozdrun.evolution.state.model.GameState;
import pl.kozdrun.evolution.state.model.PlayerState;
import pl.kozdrun.evolution.state.model.Round;

@Component
@RequiredArgsConstructor
public class GameStateManager {

    private final GameStateHolder gameStateHolder;

    public void addPlayer(String playerId) {
        gameStateHolder.getUsersGames().put(playerId, new PlayerState());
    }

    public void addGame(String playerId, GameState newGame) {
        gameStateHolder.getUsersGames()
                .get(playerId)
                .getGames()
                .add(newGame);
    }

    public GameState getGame(String playerId, String gameId) {
        return gameStateHolder.getUsersGames().get(playerId).getGame(gameId);
    }

    public boolean isNextRoundFree(String playerId, String gameId) {
        return getGame(playerId, gameId)
                .isNextRoundFree();
    }

    public void addRoundAndRecalculateBalance(String playerId, String gameId, Round round) {
        GameState gameState = getGame(playerId, gameId);
        round.setRoundNumber(gameState.getRounds().size() + 1);

        gameState.setBalance(gameState.getBalance().add(round.getScoreAmount()));
        gameState.getRounds().add(round);
    }


    public boolean userExists(String playerId) {
        return gameStateHolder.getUsersGames().containsKey(playerId);
    }

    public boolean gameExists(String playerId, String gameId) {
        return gameStateHolder.getUsersGames().get(playerId).getGame(gameId) != null;
    }

    public PlayerState getPlayer(String playerId) {
        return gameStateHolder.getUsersGames().get(playerId);
    }

    public void setNextRoundFree(String playerId, String gameId, boolean nextFreeRound) {
        getGame(playerId, gameId).setNextRoundFree(nextFreeRound);
    }
}
