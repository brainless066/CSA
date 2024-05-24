package net.brainless.csa.BlackJack;

import net.brainless.csa.CSA;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck implements ModInitializer {
    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        initializeDeck();
        shuffle();
    }

    // Initialize the deck with all 52 playing cards
    private void initializeDeck() {
        String[] shapes = {"Hearts", "Diamonds", "Clubs", "Spades"};
        for (String shape : shapes) {
            for (int number = 1; number <= 13; number++) {
                cards.add(new Card(number, shape));
            }
        }

        //shuffle the deck
        shuffle();
        CSA.LOGGER.info("Deck shuffled");

    }

    // Shuffle the deck
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Deal a card from the deck
    public Card dealCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Deck is empty");
        }
        return cards.remove(cards.size() - 1);
    }

    // Check if the deck is empty
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public void onInitialize() {
        CSA.LOGGER.info("Deck initialized");
    }
}
