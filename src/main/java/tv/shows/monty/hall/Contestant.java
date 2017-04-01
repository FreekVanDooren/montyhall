package tv.shows.monty.hall;

import tv.shows.monty.hall.model.Box;

import java.util.List;
import java.util.Random;

public class Contestant implements Contestable {

    private final Random random = new Random();

    @Override
    public Box choose(List<Box> boxes) {
        return boxes.get(random.nextInt(boxes.size()));
    }
}
