package net.brainless.csa;

import net.brainless.csa.BlackJack.BlackJackGame;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraft.server.command.CommandManager.literal;

public class CSA implements ModInitializer, ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("blackjackmod");
    private static BlackJackGame blackJackGame;

    @Override
    public void onInitialize() {
        LOGGER.info("CSA mod initialized");

        blackJackGame = new BlackJackGame();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("blackjack")
                    .then(literal("start").executes(context -> startGame(context.getSource())))
                    .then(literal("hit").executes(context -> playerHit(context.getSource())))
                    .then(literal("stand").executes(context -> playerStand(context.getSource())))
            );
        });
    }

    private int startGame(ServerCommandSource source) {
        blackJackGame.startGame();
        source.sendMessage(Text.literal("BlackJack game started!"));
        source.sendMessage(Text.literal("Type '/blackjack hit' to draw a card or '/blackjack stand' to stand."));
        return 1;
    }

    private int playerHit(ServerCommandSource source) {
        blackJackGame.playerHit();
        source.sendMessage(Text.literal("Player hits!"));
        return 1;
    }

    private int playerStand(ServerCommandSource source) {
        blackJackGame.playerStand();
        source.sendMessage(Text.literal("Player stands!"));
        return 1;
    }

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            CSA.LOGGER.info("HUD render callback triggered");
            if (blackJackGame != null) {
                blackJackGame.renderCards(drawContext, tickDelta);
            } else {
                CSA.LOGGER.error("blackJackGame is null!");
            }
        });
    }
}
