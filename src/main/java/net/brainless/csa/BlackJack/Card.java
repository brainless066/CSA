package net.brainless.csa.BlackJack;
//MinJun Oh

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
            return 10; // Face cards (Jack, Queen, King) are worth 10 points
        } else if (number == 1) {
            return 11; // Ace is worth 11 points
        } else {
            return number;
        }
    }

    @Override
    public String toString() {
        String[] names = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        return names[number - 1] + " of " + shape;
    }
}
