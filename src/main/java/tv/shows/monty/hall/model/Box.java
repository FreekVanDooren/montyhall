package tv.shows.monty.hall.model;

public class Box {
    private final Result result;

    public Box(Result result) {
        this.result = result;

    }

    public Result getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Box{" +
                "result=" + result +
                '}';
    }
}
