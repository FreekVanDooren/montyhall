package tv.shows.monty.hall.model;

import java.math.BigDecimal;

public class Summary {
    public final long nrOfMindChanges;
    public final BigDecimal winningPercentageAfterMindChange;
    public final BigDecimal winningPercentageWithoutMindChange;
    public final boolean shouldMindBeChanged;

    public Summary(long nrOfMindChanges, BigDecimal winningPercentageAfterMindChange, BigDecimal winningPercentageWithoutMindChange, boolean shouldMindBeChanged) {
        this.nrOfMindChanges = nrOfMindChanges;
        this.winningPercentageAfterMindChange = winningPercentageAfterMindChange;
        this.winningPercentageWithoutMindChange = winningPercentageWithoutMindChange;
        this.shouldMindBeChanged = shouldMindBeChanged;
    }
}
