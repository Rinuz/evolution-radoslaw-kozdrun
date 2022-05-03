package pl.kozdrun.evolution.state.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerState {

    private String id;
    private List<GameState> games = new ArrayList<>();

    public GameState getGame(String gameId) {
        return games.stream()
                .filter(gameState -> gameState.getId().equals(gameId))
                .findFirst()
                .get();
    }
}