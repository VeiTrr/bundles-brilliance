package dev.vt.neoforge;

import dev.vt.BundlesBrillianceCommon;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod(BundlesBrillianceCommon.MOD_ID)
public class BundlesBrilliance {

    public BundlesBrilliance(IEventBus eventBus) {
        eventBus.addListener(this::setup);
        BundleRegistry.register(eventBus);
    }


    private void setup(final FMLCommonSetupEvent event) {
        BundlesBrillianceCommon.init();
    }
}