//MinJun Oh

package net.brainless.csa.BlackJack;

import net.brainless.csa.CSA;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayerEntity;

public class BlackJackGame {
    private Deck deck;
    private Player player;
    private Player dealer;

    public BlackJackGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Player();
    }

    public void startGame() {
        player.receiveCard(deck.dealCard());
        dealer.receiveCard(deck.dealCard());
        player.receiveCard(deck.dealCard());
        dealer.receiveCard(deck.dealCard());
    }

    public void playerHit() {
        if (player.getScore() <= 21) {
            player.receiveCard(deck.dealCard());
        }
    }

    public void playerStand() {
        while (dealer.getScore() < 17) {
            dealer.receiveCard(deck.dealCard());
        }
    }

    public void renderCards(DrawContext drawContext, float tickDelta) {
        CSA.LOGGER.info("renderCards method called");

        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity playerEntity = client.player;

        if (playerEntity != null) {
            // Fixed positions for debugging
            int playerCardsX = 50;
            int playerCardsY = 200;

            int dealerCardsX = 50;
            int dealerCardsY = 50;

            MatrixStack matrixStack = drawContext.getMatrices();

            CSA.LOGGER.info("Rendering player cards at fixed position");
            renderPlayerCards(drawContext, matrixStack, playerCardsX, playerCardsY);

            CSA.LOGGER.info("Rendering dealer cards at fixed position");
            renderDealerCards(drawContext, matrixStack, dealerCardsX, dealerCardsY);
        } else {
            CSA.LOGGER.warn("Player entity is null, cannot render cards.");
        }
    }

    private void renderPlayerCards(DrawContext drawContext, MatrixStack matrixStack, int x, int y) {
        for (Card card : player.getHand()) {
            CSA.LOGGER.info("Rendering player card at (" + x + ", " + y + ")");
            renderCard(drawContext, matrixStack, card, x, y);
            x += 70; // Offset for next card
        }
    }

    private void renderDealerCards(DrawContext drawContext, MatrixStack matrixStack, int x, int y) {
        for (Card card : dealer.getHand()) {
            CSA.LOGGER.info("Rendering dealer card at (" + x + ", " + y + ")");
            renderCard(drawContext, matrixStack, card, x, y);
            x += 70; // Offset for next card
        }
    }

    private void renderCard(DrawContext drawContext, MatrixStack matrixStack, Card card, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        Identifier texture = new Identifier("blackjackmod", "textures/cards/" + getCardTextureName(card) + ".png");
        RenderSystem.setShaderTexture(0, texture);

        // Log the texture being rendered for debugging
        CSA.LOGGER.info("Rendering card: " + texture + " at (" + x + ", " + y + ")");

        drawContext.drawTexture(texture, x, y, 0, 0, 64, 64, 64, 64);
    }

    private String getCardTextureName(Card card) {
        String[] names = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
        return names[card.getNumber() - 1] + "_of_" + card.getShape().toLowerCase();
    }
}
