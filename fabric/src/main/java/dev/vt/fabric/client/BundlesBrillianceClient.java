package dev.vt.fabric.client;

import net.fabricmc.api.ClientModInitializer;

public class BundlesBrillianceClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		BundleRegistryClient.register();
	}
}