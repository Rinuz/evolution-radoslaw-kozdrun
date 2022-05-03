package pl.kozdrun.evolution.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.kozdrun.evolution.rest.model.request.PlayRoundRequest;
import pl.kozdrun.evolution.rest.model.response.NewPlayerResponse;
import pl.kozdrun.evolution.rest.validate.PlayersControllerValidator;
import pl.kozdrun.evolution.service.PlayersService;
import pl.kozdrun.evolution.state.model.GameState;
import pl.kozdrun.evolution.state.model.PlayMode;
import pl.kozdrun.evolution.state.model.PlayerState;
import pl.kozdrun.evolution.state.model.Round;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/players")
@RequiredArgsConstructor
public class PlayersController {

    private final PlayersService playersService;
    private final PlayersControllerValidator requestValidator;

    @PostMapping("/")
    public NewPlayerResponse createNewPlayer() {
        logger.info("Create new player");
        String playerId = playersService.newPlayer();
        return new NewPlayerResponse(playerId);
    }

    @GetMapping("/{playerId}")
    public PlayerState getPlayer(
            @PathVariable("playerId") String playerId) {

        logger.info("Get player details for playerId={}", playerId);

        requestValidator.validatePlayerId(playerId);

        return playersService.getPlayer(playerId);
    }

    @PostMapping("/{playerId}/games")
    public GameState createNewGame(
            @PathVariable("playerId") String playerId) {

        logger.info("Start new game for playerId={}", playerId);

        requestValidator.validatePlayerId(playerId);

        return playersService.newGame(playerId);
    }

    @GetMapping("/{playerId}/games/{gameId}")
    public GameState getGame(
            @PathVariable("playerId") String playerId,
            @PathVariable("gameId") String gameId) {

        logger.info("Get game details for playerId={} and gameId={}", playerId, gameId);

        requestValidator.validatePlayerIdAndGameId(playerId, gameId);

        return playersService.getGame(playerId, gameId);

    }

    @PostMapping("/{playerId}/games/{gameId}/rounds")
    public Round playRound(
            @PathVariable("playerId") String playerId,
            @PathVariable("gameId") String gameId,
            @RequestBody PlayRoundRequest playRoundRequest) {

        logger.info("Play round {} for playerId={} and gameId={}", playRoundRequest, playerId, gameId);

        requestValidator.validatePlayerIdAndGameId(playerId, gameId);
        if (PlayMode.PAID == playRoundRequest.getPlayMode()) {
            requestValidator.validateBetAmountRange(playRoundRequest.getBetAmount());
            requestValidator.validateBalance(playersService.getGameBalance(playerId, gameId), playRoundRequest.getBetAmount());
        }

        return playersService.playRound(playerId, gameId, playRoundRequest);
    }

    @GetMapping("/{playerId}/games/{gameId}/rounds/{roundNumber}")
    public Round getRound(
            @PathVariable("playerId") String playerId,
            @PathVariable("gameId") String gameId,
            @PathVariable("roundNumber") Integer roundNumber) {

        logger.info("Get details for round {} for playerId={} and gameId={}", roundNumber, playerId, gameId);

        requestValidator.validatePlayerIdAndGameId(playerId, gameId);
        requestValidator.validateRoundNumber(playerId, gameId, roundNumber);

        return playersService.getRound(playerId, gameId, roundNumber);
    }

    @GetMapping("/{playerId}/games/{gameId}/rounds")
    public List<Round> getRounds(
            @PathVariable("playerId") String playerId,
            @PathVariable("gameId") String gameId) {

        logger.info("Get rounds for playerId={} and gameId={}", playerId, gameId);

        requestValidator.validatePlayerIdAndGameId(playerId, gameId);

        return playersService.getGame(playerId, gameId).getRounds();
    }

    @GetMapping("/{playerId}/games")
    public List<GameState> getGames(
            @PathVariable("playerId") String playerId) {

        logger.info("Get games for playerId={}", playerId);

        requestValidator.validatePlayerId(playerId);

        return playersService.getPlayer(playerId).getGames();
    }
}