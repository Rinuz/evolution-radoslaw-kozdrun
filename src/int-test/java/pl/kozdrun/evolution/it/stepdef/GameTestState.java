package pl.kozdrun.evolution.it.stepdef;

import io.cucumber.spring.ScenarioScope;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
@ScenarioScope
@Data
public class GameTestState {

    private ResponseEntity lastResponseEntity;
    private Exception exception;

    private String currentPlayerId;
    private String currentGameId;

    public <T> boolean hasResponseType(Class<T> responseClass) {
        return lastResponseEntity != null && lastResponseEntity.getBody() != null && lastResponseEntity.getBody().getClass().isAssignableFrom(responseClass);
    }

    public <T> T getResponse(Class<T> responseClass) {
        if (hasResponseType(responseClass)) {
            return (T) lastResponseEntity.getBody();
        }
        return null;
    }

    public int getHttpErrorCode() {
        if (exception != null && exception instanceof HttpClientErrorException) {
            return ((HttpClientErrorException) exception).getRawStatusCode();
        } else {
            return lastResponseEntity.getStatusCodeValue();
        }
    }
}
