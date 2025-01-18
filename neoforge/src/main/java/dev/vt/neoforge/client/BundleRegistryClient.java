package dev.vt.neoforge.client;

import dev.vt.BundlesBrillianceCommon;
import dev.vt.items.BundleBrillianceItem;
import dev.vt.registry.BundleRegistryCommon;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = BundlesBrillianceCommon.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
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
