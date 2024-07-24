package dev.vt;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(BundlesBrilliance.MOD_ID)
public class BundlesBrilliance {
	public static final String MOD_ID = "bundles_brilliance";
    public static final Logger LOGGER = LoggerFactory.getLogger("bundles-brilliance");

	public BundlesBrilliance(IEventBus modEventBus, ModContainer modContainer) {
		LOGGER.info("Initializing");
		BundleRegistry.register(modEventBus);
	}
}