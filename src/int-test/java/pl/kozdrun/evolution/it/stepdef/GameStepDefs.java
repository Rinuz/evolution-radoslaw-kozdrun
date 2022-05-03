package pl.kozdrun.evolution.it.stepdef;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kozdrun.evolution.it.util.GameRestCaller;
import pl.kozdrun.evolution.it.util.GameTestUrls;
import pl.kozdrun.evolution.rest.model.request.PlayRoundRequest;
import pl.kozdrun.evolution.rest.model.response.NewPlayerResponse;
import pl.kozdrun.evolution.state.GameSimulator;
import pl.kozdrun.evolution.state.GameStateManager;
import pl.kozdrun.evolution.state.model.GameState;
import pl.kozdrun.evolution.state.model.PlayMode;
import pl.kozdrun.evolution.state.model.ScoreType;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GameStepDefs {

    @Autowired
    private GameRestCaller gameRestCaller;
    @Autowired
    private GameTestState gameTestState;
    @Autowired
    private GameSimulator gameSimulator;
    @Autowired
    private GameStateManager gameStateManager;

    @Before
    public void initialization() {
        Mockito.reset(gameSimulator);
    }

    @When("^call for create new player$")
    public void callForCreateNewPlayer() throws Throwable {
        gameRestCaller.callRest(restTemplate -> restTemplate.postForEntity(
                GameTestUrls.newPlayerUrl(),
                null, NewPlayerResponse.class));
    }

    @Then("^new playerId is returned$")
    public void newPlayerIdIsReturned() throws Throwable {
        NewPlayerResponse newPlayerResponse = gameTestState.getResponse(NewPlayerResponse.class);
        assertThat(newPlayerResponse).isNotNull();
        assertThat(newPlayerResponse.getPlayerId()).isNotBlank();
        gameTestState.setCurrentPlayerId(newPlayerResponse.getPlayerId());
    }

    @When("^call for create new game$")
    public void callForCreateNewGame() throws Throwable {
        gameRestCaller.callRest(restTemplate -> restTemplate.postForEntity(
                GameTestUrls.newGameUrl(gameTestState.getCurrentPlayerId()),
                null, GameState.class));
    }

    @Then("^new gameId is returned$")
    public void newGameIdIsReturned() throws Throwable {
        GameState newGameResponse = gameTestState.getResponse(GameState.class);
        assertThat(newGameResponse).isNotNull();
        assertThat(newGameResponse.getId()).isNotBlank();
        gameTestState.setCurrentGameId(newGameResponse.getId());
    }

    @And("^create new player and game$")
    public void createNewPlayerAndGame() throws Throwable {
        callForCreateNewPlayer();
        newPlayerIdIsReturned();
        callForCreateNewGame();
        newGameIdIsReturned();
    }

    @When("play {word} round with {bigdecimal} euro and {word} result")
    public void playPaidRound(String playModeString, BigDecimal betAmount, String winTypeString) throws Throwable {
        PlayRoundRequest playRoundRequest = new PlayRoundRequest();
        playRoundRequest.setPlayMode(PlayMode.valueOf(playModeString));
        playRoundRequest.setBetAmount(betAmount);

        when(gameSimulator.playRound()).thenReturn(ScoreType.valueOf(winTypeString));

        gameRestCaller.callRest(restTemplate -> restTemplate.postForEntity(
                GameTestUrls.playRoundUrl(gameTestState.getCurrentPlayerId(), gameTestState.getCurrentGameId()),
                playRoundRequest,
                NewPlayerResponse.class));
    }

    @When("game balance is equals to {bigdecimal}")
    public void getGameState(BigDecimal expectedBalance) throws Throwable {
        gameRestCaller.callRest(restTemplate -> restTemplate.getForEntity(
                GameTestUrls.gameStateUrl(gameTestState.getCurrentPlayerId(), gameTestState.getCurrentGameId()),
                GameState.class));
        GameState gameStateResponse = gameTestState.getResponse(GameState.class);
        assertThat(gameStateResponse.getBalance()).isEqualByComparingTo(expectedBalance);
    }

    @And("^next round will be free$")
    public void setNextRoundAsFree() throws Throwable {
        when(gameSimulator.drawFreeRound()).thenReturn(true);
    }

    @And("http response status code is {int}")
    public void checkHttpResponseCode(int responseStatusCode) throws Throwable {
        assertThat(gameTestState.getHttpErrorCode()).isEqualTo(responseStatusCode);
    }

    @When("play {word} round with {bigdecimal} euro and {word} result {int} times")
    public void playPaidRounds(String playModeString, BigDecimal betAmount, String winTypeString, int roundNumber) throws Throwable {
        for (int i = 0; i < roundNumber; i++) {
            playPaidRound(playModeString, betAmount, winTypeString);
        }
    }

    @And("change game balance to {bigdecimal}")
    public void changeGameBalance(BigDecimal newGameBalance) {
        gameStateManager.getGame(gameTestState.getCurrentPlayerId(), gameTestState.getCurrentGameId()).setBalance(newGameBalance);
    }
}