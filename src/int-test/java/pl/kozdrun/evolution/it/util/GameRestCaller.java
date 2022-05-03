package pl.kozdrun.evolution.it.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.kozdrun.evolution.it.stepdef.GameTestState;

import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class GameRestCaller {

    private final RestTemplate restTemplate;
    private final GameTestState testState;

    public void callRest(Function<RestTemplate, ResponseEntity> restCall) {
        try {
            ResponseEntity responseEntity = restCall.apply(restTemplate);
            testState.setLastResponseEntity(responseEntity);
        } catch (Exception e) {
            testState.setException(e);
            logger.error("Rest call error: {}", e.getMessage());
        }
    }
}