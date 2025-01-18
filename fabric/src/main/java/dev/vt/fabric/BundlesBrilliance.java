package dev.vt.fabric;

import dev.vt.BundlesBrillianceCommon;
import net.fabricmc.api.ModInitializer;

public class BundlesBrilliance implements ModInitializer {

	@Override
	public void onInitialize() {
		BundlesBrillianceCommon.init();
		BundleRegistry.register();
	}
}