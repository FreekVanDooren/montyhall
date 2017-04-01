package tv.shows.monty.hall;

import tv.shows.monty.hall.model.Outcome;
import tv.shows.monty.hall.model.Summary;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class OutcomeSummarizer {
    private List<Outcome> outcomes = new ArrayList<>();

    public void collect(Outcome outcome) {
        this.outcomes.add(outcome);
    }

    public Summary summary() {
        BigDecimal winningPercentageAfterMindChange = winningPercentageAfterMindChange();
        BigDecimal winningPercentageWithoutMindChange = winningPercentageWithoutMindChange();
        boolean shouldMindBeChanged = winningPercentageAfterMindChange.compareTo(winningPercentageWithoutMindChange) >= 0;
        return new Summary(
                nrOfPlays(Outcome::isOtherBoxChosen),
                winningPercentageAfterMindChange,
                winningPercentageWithoutMindChange,
                shouldMindBeChanged);
    }

    private BigDecimal winningPercentageAfterMindChange() {
        return calculatePercentage(Outcome::isOtherBoxChosen);
    }

    private BigDecimal winningPercentageWithoutMindChange() {
        return calculatePercentage(Outcome::isSameBoxChosen);
    }

    private BigDecimal calculatePercentage(Predicate<Outcome> predicate) {
        long nrOfWins = outcomes.stream()
                .filter(predicate)
                .filter(Outcome::isWin)
                .count();
        long nrOfPlays = nrOfPlays(predicate);
        if (nrOfPlays == 0L) {
            return new BigDecimal("0.00");
        }
        return new BigDecimal(nrOfWins)
                .divide(new BigDecimal(nrOfPlays), MathContext.DECIMAL32)
                .multiply(new BigDecimal(100))
                .setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }

    private long nrOfPlays(Predicate<Outcome> predicate) {
        return outcomes.stream()
                .filter(predicate)
                .count();
    }
}
