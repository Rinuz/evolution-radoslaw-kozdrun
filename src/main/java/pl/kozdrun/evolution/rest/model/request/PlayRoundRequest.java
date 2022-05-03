package pl.kozdrun.evolution.rest.model.request;

import lombok.Data;
import pl.kozdrun.evolution.state.model.PlayMode;

import java.math.BigDecimal;

@Data
public class PlayRoundRequest {

    private BigDecimal betAmount;
    private PlayMode playMode;
}