package tv.shows.monty.hall;

import tv.shows.monty.hall.model.Box;
import tv.shows.monty.hall.model.Outcome;
import tv.shows.monty.hall.model.Summary;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static tv.shows.monty.hall.model.Result.LOSS;
import static tv.shows.monty.hall.model.Result.WIN;

public class Show {

    public static final int DEFAULT_NR_OF_SHOWS = 100000;

    public static void main(String[] args) {
        Contestant contestant = new Contestant();
        OutcomeSummarizer summarizer = new OutcomeSummarizer();
        Show show = new Show();

        int nrOfShows = DEFAULT_NR_OF_SHOWS;
        if (args.length > 0) {
            nrOfShows = Integer.parseInt(args[0]);
        }

        for (int i = 0; i < nrOfShows; i++) {
            if (i % 50000 == 0) {
                System.out.println(String.format("%s games played.", i));
            }
            summarizer.collect(show.play(contestant));
        }

        Summary summary = summarizer.summary();

        System.out.println("==============================================================");
        System.out.println(String.format("Results after %s shows:", nrOfShows));
        System.out.println("Total nr of mind changes: " + summary.nrOfMindChanges);
        System.out.println(String.format("Wins after change of mind: %s %%", summary.winningPercentageAfterMindChange));
        System.out.println(String.format("Wins without change of mind: %s %%", summary.winningPercentageWithoutMindChange));
        System.out.println("Do I stand a better chance to win if I change my mind? " + (summary.shouldMindBeChanged ? "YES" : "NO"));
        System.out.println("==============================================================");
    }

    private static final Box WINNING_BOX = new Box(WIN);
    private List<Box> boxes = asList(new Box(LOSS), WINNING_BOX, new Box(LOSS));

    public Outcome play(Contestable contestable) {
        Box firstChosenBox = contestable.choose(boxes);
        Box secondChosenBox = openBoxAndChooseAgain(contestable, firstChosenBox);
        return new Outcome(secondChosenBox.getResult(), firstChosenBox == secondChosenBox);
    }

    private Box openBoxAndChooseAgain(Contestable contestable, Box chosenBox) {
        return contestable.choose(
            asList(
                chosenBox,
                getABoxThatWasNotChosen(chosenBox)
            )
        );
    }

    private Box getABoxThatWasNotChosen(Box pickedBox) {
        return boxes
                .stream()
                .filter(box -> box != pickedBox)
                .filter(box -> box == WINNING_BOX)
                .findFirst()
                .orElseGet(() ->
                    boxes.stream()
                            .filter(box -> box != pickedBox)
                            .findFirst()
                            .get()
                );
    }
}
