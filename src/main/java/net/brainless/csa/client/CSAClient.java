package net.brainless.csa.client;

import net.fabricmc.api.ClientModInitializer;

public class CSAClient  implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("Hello from CSA Client!");
    }
}
