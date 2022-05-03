package pl.kozdrun.evolution.state.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Round {

    private long roundNumber;
    private BigDecimal betAmount;
    private BigDecimal scoreAmount;
    private PlayMode playMode = PlayMode.PAID;
    private ScoreType scoreType;
    private boolean freeRound;
}