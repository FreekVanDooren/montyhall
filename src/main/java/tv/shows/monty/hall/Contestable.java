package tv.shows.monty.hall;

import tv.shows.monty.hall.model.Box;

import java.util.List;

public interface Contestable {
    Box choose(List<Box> boxes);
}
