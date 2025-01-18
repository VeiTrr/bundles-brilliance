package dev.vt.forge.client;

import dev.vt.BundlesBrillianceCommon;
import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = BundlesBrillianceCommon.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BundleRegistryClient {


    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.MINERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.ALCHEMISTS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.BUILDERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistryCommon.FARMERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        registerModelPredicates();
    }
}
