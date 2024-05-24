package net.brainless.csa.BlackJack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Card class representing a single playing card
public class Card {
    private final int number;
    private final String shape;

    public Card(int number, String shape) {
        this.number = number;
        this.shape = shape;
    }

    public int getNumber() {
        return number;
    }

    public String getShape() {
        return shape;
    }

    @Override
    public String toString() {
        return number + " of " + shape;
    }
}

