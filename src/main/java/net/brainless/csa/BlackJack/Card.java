package net.brainless.csa.BlackJack;

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

    public int getValue() {
        if (number > 10) {
            return 10;
        } else if (number == 1) {
            return 11;
        } else {
            return number;
        }
    }

    public String getTexturePath() {
        String rank = switch (number) {
            case 1 -> "ace";
            case 11 -> "jack";
            case 12 -> "queen";
            case 13 -> "king";
            default -> String.valueOf(number);
        };
        return "CSA:textures/cards/" + rank + "_of_" + shape.toLowerCase() + ".png";
    }

    @Override
    public String toString() {
        return number + " of " + shape;
    }

    public Object getRank() {
        return switch (number) {
            case 1 -> "Ace";
            case 11 -> "Jack";
            case 12 -> "Queen";
            case 13 -> "King";
            default -> number;
        };
    }

    public String getName() {
        return switch (number) {
            case 1 -> "ace_of_" + shape.toLowerCase();
            case 11 -> "jack_of_" + shape.toLowerCase();
            case 12 -> "queen_of_" + shape.toLowerCase();
            case 13 -> "king_of_" + shape.toLowerCase();
            default -> number + "_of_" + shape.toLowerCase();
        };
    }
}
