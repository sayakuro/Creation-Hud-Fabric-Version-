package net.fatelesshub;

import net.fabricmc.api.ClientModInitializer;

import static com.mojang.text2speech.Narrator.LOGGER;


public class FLTHUBClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        LOGGER.info("Initializing {HUB}...");
    }
}
