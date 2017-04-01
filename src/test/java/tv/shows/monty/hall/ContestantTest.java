package tv.shows.monty.hall;

import org.junit.Test;
import tv.shows.monty.hall.model.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;
import static tv.shows.monty.hall.model.Result.LOSS;

public class ContestantTest {

    @Test
    public void canPickABoxRandomlyFromListsOfDifferentSizes() throws Exception {
        assertContestantCanChoose(listOfSize(2));
        assertContestantCanChoose(listOfSize(3));
    }

    private void assertContestantCanChoose(List<Box> boxes) {
        Box chosenBox = new Contestant().choose(boxes);

        assertThat(boxes, hasItem(chosenBox));
    }

    private List<Box> listOfSize(int size) {
        return IntStream
                .range(0,size)
                .mapToObj((i) -> new Box(LOSS))
                .collect(toList());
    }

}