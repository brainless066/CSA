package net.brainless.csa.BlackJack;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class BlackJackGame {
    private static final Logger LOGGER = LogManager.getLogger("blackjackmod");
    private Deck deck;
    private List<Card> dealerHand;
    private List<Card> playerHand;
    private boolean playerTurn;
    private boolean gameOver;

    public BlackJackGame() {
        resetGame();
    }

    public void startGame() {
        resetGame();
        // Deal initial cards
        playerHand.add(deck.dealCard());
        playerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());
        dealerHand.add(deck.dealCard());

        // Show initial hands
        LOGGER.info("Player's hand: " + playerHand);
        LOGGER.info("Dealer's hand: [" + dealerHand.get(0) + ", Hidden]");
    }

    private void playerTurn() {
        // Player turn logic should wait for player input
        if (isBust(playerHand)) {
            LOGGER.info("Player busts! Dealer wins.");
            gameOver = true;
            determineWinner();
        }
    }

    public void hit() {
        if (playerTurn && !gameOver) {
            playerHand.add(deck.dealCard());
            LOGGER.info("Player's hand: " + playerHand);

            // Send message to player that they lost if they bust
            if (isBust(playerHand)) {
                MinecraftClient client = MinecraftClient.getInstance();
                client.player.sendMessage(Text.of("You lost! Dealer wins."), false);
                gameOver = true;
                determineWinner();
            } else {
                playerTurn();
            }
        }
    }

    public void stand() {
        if (playerTurn && !gameOver) {
            playerTurn = false;
            dealerTurn();
        }
    }

    private void dealerTurn() {
        // Dealer turn logic
        while (getHandValue(dealerHand) < 17) {
            dealerHand.add(deck.dealCard());
        }
        determineWinner();
        gameOver = true;
    }

    private int getHandValue(List<Card> hand) {
        // Calculate hand value
        int value = 0;
        int aces = 0;
        for (Card card : hand) {
            value += card.getValue();
            if (card.getRank().equals("Ace")) {
                aces++;
            }
        }
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }

    private boolean isBust(List<Card> hand) {
        return getHandValue(hand) > 21;
    }

    private void determineWinner() {
        int playerValue = getHandValue(playerHand);
        int dealerValue = getHandValue(dealerHand);

        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity playerEntity = client.player;

        if (isBust(playerHand)) {
            LOGGER.info("Player busts! Dealer wins.");
            if (playerEntity != null) {
                playerEntity.sendMessage(Text.of("You busted! Dealer wins."), false);
            }
        } else if (isBust(dealerHand)) {
            LOGGER.info("Dealer busts! Player wins.");
            if (playerEntity != null) {
                playerEntity.sendMessage(Text.of("Dealer busted! You win."), false);
            }
        } else if (playerValue > dealerValue) {
            LOGGER.info("Player wins with " + playerValue + " against dealer's " + dealerValue + ".");
            if (playerEntity != null) {
                playerEntity.sendMessage(Text.of("You win with " + playerValue + " against dealer's " + dealerValue + "."), false);
            }
        } else if (playerValue < dealerValue) {
            LOGGER.info("Dealer wins with " + dealerValue + " against player's " + playerValue + ".");
            if (playerEntity != null) {
                playerEntity.sendMessage(Text.of("Dealer wins with " + dealerValue + " against your " + playerValue + "."), false);
            }
        } else {
            LOGGER.info("It's a tie with both player and dealer having " + playerValue + ".");
            if (playerEntity != null) {
                playerEntity.sendMessage(Text.of("It's a tie with both you and the dealer having " + playerValue + "."), false);
            }
        }
    }

    private void resetGame() {
        deck = new Deck();
        if (dealerHand == null) {
            dealerHand = new ArrayList<>();
        } else {
            dealerHand.clear();
        }

        if (playerHand == null) {
            playerHand = new ArrayList<>();
        } else {
            playerHand.clear();
        }

        playerTurn = true;
        gameOver = false;
        LOGGER.info("Game reset. Ready for a new game.");
    }

    public void renderCards(DrawContext drawContext, float tickDelta) {
        LOGGER.info("renderCards method called");

        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity playerEntity = client.player;

        if (playerEntity != null) {
            MatrixStack matrixStack = drawContext.getMatrices();

            if (!gameOver) {
                // Fixed positions for debugging
                int playerCardsX = 50;
                int playerCardsY = 200;

                int dealerCardsX = 50;
                int dealerCardsY = 50;

                LOGGER.info("Rendering player cards at fixed position");
                renderPlayerCards(drawContext, matrixStack, playerCardsX, playerCardsY);

                LOGGER.info("Rendering dealer cards at fixed position");
                renderDealerCards(drawContext, matrixStack, dealerCardsX, dealerCardsY);
            } else {
                LOGGER.info("Game over. Not rendering cards.");
            }
        } else {
            LOGGER.warn("Player entity is null, cannot render cards.");
        }
    }

    private void renderPlayerCards(DrawContext drawContext, MatrixStack matrixStack, int x, int y) {
        for (Card card : playerHand) {
            renderCard(drawContext, matrixStack, card, x, y);
            x += 70; // Offset for next card
        }
    }

    private void renderDealerCards(DrawContext drawContext, MatrixStack matrixStack, int x, int y) {
        for (Card card : dealerHand) {
            renderCard(drawContext, matrixStack, card, x, y);
            x += 70; // Offset for next card
        }
    }

    private void renderCard(DrawContext drawContext, MatrixStack matrixStack, Card card, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        Identifier texture = new Identifier("blackjackmod", "textures/cards/" + getCardTextureName(card) + ".png");
        RenderSystem.setShaderTexture(0, texture);

        drawContext.drawTexture(texture, x, y, 0, 0, 64, 64, 64, 64);
    }

    private String getCardTextureName(Card card) {
        // Assume card.getName() returns the appropriate name for the texture file
        return card.getName();
    }
}
