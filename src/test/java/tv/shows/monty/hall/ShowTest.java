package tv.shows.monty.hall;

import org.junit.Test;
import tv.shows.monty.hall.model.Box;
import tv.shows.monty.hall.model.Outcome;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;
import static tv.shows.monty.hall.model.Result.LOSS;
import static tv.shows.monty.hall.model.Result.WIN;

public class ShowTest {

    private Box chosenBox;
    private Contestable contestant;
    private Outcome outcome;

    @Test
    public void contestantCanPlay() throws Exception {
        contestant = boxes -> {
            assertWinningBoxIsOffered(boxes);
            return boxes.stream().findFirst().get();
        };

        whenTheShowIsPlayed();

        assertNotNull(outcome);
    }

    @Test
    public void whenContestantPicksPriceOutcomeIsWin() throws Exception {
        contestant = boxes -> {
            assertWinningBoxIsOffered(boxes);
            return boxes.stream().filter((box -> box.getResult() == WIN))
                    .findFirst().get();
        };

        whenTheShowIsPlayed();

        assertTrue(outcome.isWin());
    }

    @Test
    public void whenContestantDoesNotPickPriceOutcomeIsLose() throws Exception {
        contestant = boxes -> {
            assertWinningBoxIsOffered(boxes);
            return boxes.stream().filter((box -> box.getResult() == LOSS))
                    .findFirst().get();
        };

        whenTheShowIsPlayed();

        assertFalse(outcome.isWin());
    }

    @Test
    public void afterFirstPickContestantIsOfferedASecondChoiceAndChoosesTheSameBox() throws Exception {
        contestant = boxes -> {
            assertWinningBoxIsOffered(boxes);
            if (chosenBox == null) {
                chosenBox = boxes.stream().findFirst().get();
                return chosenBox;
            }
            return boxes.stream().filter((box) -> box == chosenBox).findFirst().get();
        };

        whenTheShowIsPlayed();

        assertFalse(outcome.isOtherBoxChosen());
        assertTrue(outcome.isSameBoxChosen());
    }

    @Test
    public void afterFirstPickContestantIsOfferedASecondChoiceAndChoosesTheOtherBox() throws Exception {
        contestant = boxes -> {
            assertWinningBoxIsOffered(boxes);
            if (chosenBox == null) {
                chosenBox = boxes.stream().findFirst().get();
                return chosenBox;
            }
            return boxes.stream().filter((box) -> box != chosenBox).findFirst().get();
        };

        whenTheShowIsPlayed();

        assertTrue(outcome.isOtherBoxChosen());
        assertFalse(outcome.isSameBoxChosen());
    }

    private void assertWinningBoxIsOffered(List<Box> boxes) {
        assertThat(boxes.stream()
                .map(Box::getResult)
                .collect(toList()), hasItem(WIN));
    }

    private void whenTheShowIsPlayed() {
        outcome = new Show().play(contestant);
    }

}