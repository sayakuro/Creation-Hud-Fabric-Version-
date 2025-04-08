package net.fatelesshub;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FLTHubMod implements ModInitializer {
	public static final String MOD_ID = "fateless";
	public static final String MOD_NAME = "FatelesHUB";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		LOGGER.info("Fateless HUB is loading...");
	}
}