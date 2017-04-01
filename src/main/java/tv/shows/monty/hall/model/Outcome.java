package tv.shows.monty.hall.model;

public class Outcome {

    private final Result result;
    private final boolean sameBoxChosen;


    public Outcome(Result result, boolean sameBoxChosen) {
        this.result = result;
        this.sameBoxChosen = sameBoxChosen;
    }

    public boolean isOtherBoxChosen() {
        return !sameBoxChosen;
    }

    public boolean isSameBoxChosen() {
        return sameBoxChosen;
    }

    public boolean isWin() {
        return result == Result.WIN;
    }
}
