package pl.kozdrun.evolution.state;

import org.springframework.stereotype.Component;
import pl.kozdrun.evolution.state.model.PlayerState;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public final class GameStateHolder {

    private final Map<String, PlayerState> playersMap = new ConcurrentHashMap<>();

    public Map<String, PlayerState> getUsersGames() {
        return playersMap;
    }
}