package net.brainless.csa.BlackJack;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void receiveCard(Card card) {
        hand.add(card);
    }

    public int getScore() {
        int score = 0;
        int aces = 0;
        for (Card card : hand) {
            score += card.getValue();
            if (card.getNumber() == 1) {
                aces++;
            }
        }
        // Adjust for Aces value (1 or 11)
        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }
        return score;
    }

    public List<Card> getHand() {
        return hand;
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
