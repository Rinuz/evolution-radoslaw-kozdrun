package pl.kozdrun.evolution.state.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RoundScore {

    private ScoreType scoreType;
    private BigDecimal betAmount;
    private PlayMode playMode;
}