package dev.vt.client;

import dev.vt.BundleRegistry;
import dev.vt.items.BundleBrillianceItem;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;


public class BundleRegistryClient {

    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(BundleRegistry.MINERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistry.ALCHEMISTS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistry.BUILDERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(BundleRegistry.FARMERS_BUNDLE, Identifier.ofVanilla("filled"), (stack, world, entity, seed) -> BundleBrillianceItem.getAmountFilled(stack));
    }


    public static void register() {
        registerModelPredicates();
    }
}
