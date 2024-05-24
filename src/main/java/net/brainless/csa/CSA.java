package net.brainless.csa;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSA implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("CSA");
    public static CSA CONFIG;
    public static final String MOD_ID = "ttugbaegi0521";
    //instance of MinecraftClient
    public static final CSA INSTANCE = new CSA();
    @Override
    public void onInitialize() {
        LOGGER.info("Hello Fabric world!");

    }

}
