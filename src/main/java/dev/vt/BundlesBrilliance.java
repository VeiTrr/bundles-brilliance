package dev.vt;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundlesBrilliance implements ModInitializer {
	public static final String MOD_ID = "bundles_brilliance";
    public static final Logger LOGGER = LoggerFactory.getLogger("bundles-brilliance");

	@Override
	public void onInitialize() {
		BundleRegistry.register();
	}
}