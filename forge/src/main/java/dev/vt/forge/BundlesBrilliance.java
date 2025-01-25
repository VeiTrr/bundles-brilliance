package dev.vt.forge;

import dev.vt.BundlesBrillianceCommon;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BundlesBrillianceCommon.MOD_ID)
public class BundlesBrilliance {

    public BundlesBrilliance() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        BundleRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


    private void setup(final FMLCommonSetupEvent event) {
        BundlesBrillianceCommon.init();
    }
}