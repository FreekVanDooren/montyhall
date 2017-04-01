package tv.shows.monty.hall;

import org.junit.Before;
import org.junit.Test;
import tv.shows.monty.hall.model.Outcome;
import tv.shows.monty.hall.model.Result;
import tv.shows.monty.hall.model.Summary;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static tv.shows.monty.hall.model.Result.LOSS;
import static tv.shows.monty.hall.model.Result.WIN;

public class OutcomeSummarizerTest {

    private OutcomeSummarizer summarizer;
    private Summary summary;

    @Before
    public void setUp() throws Exception {
        summarizer = new OutcomeSummarizer();
    }

    @Test
    public void canSummarizeOnlyOneGamePlayed() throws Exception {
        collectLossAfterMindChange();

        summarize();

        assertEquals(1, summary.nrOfMindChanges);
        assertEquals(new BigDecimal("0.00"), summary.winningPercentageAfterMindChange);
        assertEquals(new BigDecimal("0.00"), summary.winningPercentageWithoutMindChange);
        assertTrue(summary.shouldMindBeChanged);
    }

    @Test
    public void countsNrOfMindChanges() throws Exception {
        collectWinAfterMindChange();
        collectWinWithoutMindChange();
        collectLossAfterMindChange();
        collectLossWithoutMindChange();

        summarize();

        assertEquals(2, summary.nrOfMindChanges);
    }

    @Test
    public void calculatesWinningPercentageAfterMindChange() throws Exception {
        collectWinAfterMindChange();
        collectLossAfterMindChange();
        collectLossAfterMindChange();
        collectWinWithoutMindChange();
        collectLossWithoutMindChange();

        summarize();

        assertEquals(new BigDecimal("33.33"), summary.winningPercentageAfterMindChange);
    }

    @Test
    public void calculatesWinningPercentageWithoutMindChange() throws Exception {
        collectWinWithoutMindChange();
        collectWinWithoutMindChange();
        collectWinWithoutMindChange();
        collectWinWithoutMindChange();
        collectWinWithoutMindChange();
        collectLossWithoutMindChange();

        collectWinAfterMindChange();
        collectLossAfterMindChange();

        summarize();

        assertEquals(new BigDecimal("83.33"), summary.winningPercentageWithoutMindChange);
    }

    @Test
    public void willSayMindShouldBeChangedIfHigherOrSamePercentageWithMindChange() throws Exception {
        collectWinAfterMindChange();
        collectWinWithoutMindChange();

        summarize();

        assertTrue(summary.shouldMindBeChanged);
    }

    @Test
    public void willSayMindShouldNotBeChangedIfLowerPercentageWithMindChange() throws Exception {
        collectWinAfterMindChange();
        collectLossAfterMindChange();
        collectWinWithoutMindChange();

        summarize();

        assertFalse(summary.shouldMindBeChanged);
    }

    private void collectWinAfterMindChange() {
        collect(WIN, false);
    }

    private void collectWinWithoutMindChange() {
        collect(WIN, true);
    }

    private void collectLossAfterMindChange() {
        collect(LOSS, false);
    }

    private void collectLossWithoutMindChange() {
        collect(LOSS, true);
    }

    private void collect(Result result, boolean sameBoxChosen) {
        summarizer.collect(new Outcome(result, sameBoxChosen));
    }

    private void summarize() {
        summary = summarizer.summary();
    }
}