package pl.kozdrun.evolution.state.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class GameState {

    private String id;
    private BigDecimal balance;
    private boolean nextRoundFree;
    private List<Round> rounds = new ArrayList<>();
}