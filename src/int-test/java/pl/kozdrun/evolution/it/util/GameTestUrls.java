package pl.kozdrun.evolution.it.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GameTestUrls {

    private static final String HOST_URL = "http://localhost:8082";
    private static final String API_URL = "/api/v1/players";

    public String newPlayerUrl() {
        return HOST_URL + API_URL + "/";
    }

    public String newGameUrl(String playerId) {
        return HOST_URL + API_URL + "/" + playerId + "/games";
    }

    public String playRoundUrl(String playerId, String gameId) {
        return HOST_URL + API_URL + "/" + playerId + "/games/" + gameId + "/rounds";
    }

    public String gameStateUrl(String playerId, String gameId) {
        return HOST_URL + API_URL + "/" + playerId + "/games/" + gameId;
    }
}
