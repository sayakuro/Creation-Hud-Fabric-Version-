package net.creationhud;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CTHubMod implements ModInitializer {
	public static final String MOD_ID = "creationhud";
	public static final String MOD_NAME = "Creation HUB";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {


		LOGGER.info(MOD_NAME + " loading...");
	}
}