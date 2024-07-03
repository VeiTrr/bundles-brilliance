package dev.vt.items;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvents;

import java.util.List;

public class BundleBrillianceItem extends BundleItem {

    public BundleBrillianceItem() {
        super(new Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT));
    }

    public boolean insertStack(ItemStack bundleStack, ItemStack insertStack, PlayerEntity player) {
        BundleContentsComponent bundleContentsComponent = bundleStack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent == null || bundleContentsComponent.getOccupancy().floatValue() >= 1 || !checkValid(insertStack)) {
            return false;
        }

        ItemStack itemStack = insertStack;
        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
        if (itemStack.isEmpty()) {
            player.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8f, 0.8f + player.getWorld().getRandom().nextFloat() * 0.4f);
            ItemStack itemStack2 = builder.removeFirst();
            if (itemStack2 != null) {
                ItemStack itemStack3 = itemStack2;
                builder.add(itemStack3);
            }
        } else if (itemStack.getItem().canBeNested() && builder.add(insertStack) > 0) {
            player.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8f, 0.8f + player.getWorld().getRandom().nextFloat() * 0.4f);
        }
        bundleStack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
        return true;
    }

    public boolean checkValid(ItemStack insertStack) {
        return true;
    }

    public static class AlchemistsBundleItem extends BundleBrillianceItem {
        public AlchemistsBundleItem() {
            super();
        }

        @Override
        public boolean checkValid(ItemStack insertStack) {
            //TODO: Replace with config
            List<TagKey<Item>> tags = List.of(
                    ConventionalItemTags.POTIONS
            );

            for (TagKey<Item> tag : tags) {
                if (TagUtil.isIn(tag, insertStack.getItem())) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class BuildersBundleItem extends BundleBrillianceItem {
        public BuildersBundleItem() {
            super();
        }

        @Override
        public boolean checkValid(ItemStack insertStack) {
            //TODO: Replace with config
            return insertStack.getItem() instanceof BlockItem;
        }
    }

    public static class FarmersBundleItem extends BundleBrillianceItem {
        public FarmersBundleItem() {
            super();
        }

        @Override
        public boolean checkValid(ItemStack insertItem) {
            //TODO: Replace with config
            List<TagKey<Item>> tags = List.of(
                    ConventionalItemTags.BERRIES_FOODS,
                    ConventionalItemTags.FRUITS_FOODS,
                    ConventionalItemTags.VEGETABLES_FOODS,
                    ItemTags.CHICKEN_FOOD,
                    ItemTags.PIG_FOOD,
                    ItemTags.COW_FOOD,
                    ItemTags.SHEEP_FOOD,
                    ItemTags.RABBIT_FOOD,
                    ItemTags.HORSE_FOOD,
                    ItemTags.TURTLE_FOOD,
                    ItemTags.PANDA_FOOD,
                    ItemTags.FOX_FOOD
            );

            for (TagKey<Item> tag : tags) {
                if (TagUtil.isIn(tag, insertItem.getItem())) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class MinersBundleItem extends BundleBrillianceItem {
        public MinersBundleItem() {
            super();
        }

        @Override
        public boolean checkValid(ItemStack insertStack) {
            //TODO: Replace with config
            List<TagKey<Item>> tags = List.of(
                    ConventionalItemTags.ORES,
                    ConventionalItemTags.INGOTS,
                    ConventionalItemTags.RAW_MATERIALS,
                    ConventionalItemTags.RAW_BLOCKS,
                    ConventionalItemTags.GEMS,
                    ItemTags.COALS,
                    ConventionalItemTags.NUGGETS
            );
            for (TagKey<Item> tag : tags) {
                if (TagUtil.isIn(tag, insertStack.getItem())) {
                    return true;
                }
            }
            return false;
        }
    }
}
