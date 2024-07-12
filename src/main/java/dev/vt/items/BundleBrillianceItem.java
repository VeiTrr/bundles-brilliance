package dev.vt.items;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.fabric.api.tag.convention.v2.TagUtil;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;

import java.util.List;

public class BundleBrillianceItem extends BundleItem {

    public BundleBrillianceItem() {
        super(new Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, BundleContentsComponent.DEFAULT));
    }

    public BundleBrillianceItem(Settings settings) {
        super(settings);
    }

    public boolean insertStack(ItemStack bundleStack, ItemStack insertStack, PlayerEntity player) {
        BundleContentsComponent bundleContentsComponent = bundleStack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent == null || bundleContentsComponent.getOccupancy().floatValue() >= 1 || !checkValid(insertStack)) {
            return false;
        }

        ItemStack itemStack = insertStack;
        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
        if (itemStack.isEmpty()) {
            playRemoveOneSound(player);
            ItemStack itemStack2 = builder.removeFirst();
            if (itemStack2 != null) {
                ItemStack itemStack3 = itemStack2;
                builder.add(itemStack3);
            }
        } else if (itemStack.getItem().canBeNested() && builder.add(insertStack) > 0) {
            playInsertSound(player);
        }
        bundleStack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
        return true;
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        int i;
        if (clickType != ClickType.RIGHT) {
            return false;
        }
        BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent == null) {
            return false;
        }
        ItemStack itemStack = slot.getStack();
        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
        if (itemStack.isEmpty()) {
            this.playRemoveOneSound(player);
            ItemStack itemStack2 = builder.removeFirst();
            if (itemStack2 != null) {
                ItemStack itemStack3 = slot.insertStack(itemStack2);
                builder.add(itemStack3);
            }
        } else if (checkValid(itemStack)) {
            if (itemStack.getItem().canBeNested() && (i = builder.add(slot, player)) > 0) {
                this.playInsertSound(player);
            }
        }
        stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
        return true;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.RIGHT || !slot.canTakePartial(player)) {
            return false;
        }
        BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
        if (bundleContentsComponent == null) {
            return false;
        }
        BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
        if (otherStack.isEmpty()) {
            ItemStack itemStack = builder.removeFirst();
            if (itemStack != null) {
                this.playRemoveOneSound(player);
                cursorStackReference.set(itemStack);
            }
        } else if (checkValid(otherStack)) {
            int i = builder.add(otherStack);
            if (i > 0) {
                this.playInsertSound(player);
            }
        }
        stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
        return true;
    }

    public boolean checkValid(ItemStack insertStack) {
        return true;
    }

    public static class AlchemistsBundleItem extends BundleBrillianceItem {


        public AlchemistsBundleItem() {
            super(new Settings().maxCount(1).component(DataComponentTypes.BUNDLE_CONTENTS, new BundleContentsComponent(List.of())));
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

        @Override
        public boolean insertStack(ItemStack bundleStack, ItemStack insertStack, PlayerEntity player) {
            if (insertStack.getItem() instanceof PotionItem) {
                if (insertStack.getComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1) < 16) {
                    ComponentMap insertStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, 16).build();
                    insertStack.applyComponentsFrom(insertStackComponent);
                }
            }
            return super.insertStack(bundleStack, insertStack, player);
        }

        @Override
        public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
            int i;
            if (clickType != ClickType.RIGHT) {
                return false;
            }
            BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent == null) {
                return false;
            }
            ItemStack itemStack = slot.getStack();
            BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
            if (itemStack.isEmpty()) {
                super.playRemoveOneSound(player);
                ItemStack itemStack2 = builder.removeFirst();
                if (itemStack2 != null) {
                    if (itemStack2.getCount() > 1 && itemStack2.getDefaultComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1) < 16) {
                        ItemStack itemStack3 = itemStack2.split(1);
                        ComponentMap itemStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, itemStack3.getDefaultComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1)).build();
                        itemStack3.applyComponentsFrom(itemStackComponent);
                        slot.setStack(itemStack3);
                        builder.add(itemStack2);
                    } else {
                        ComponentMap itemStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, itemStack2.getDefaultComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1)).build();
                        itemStack2.applyComponentsFrom(itemStackComponent);
                        slot.setStack(itemStack2);
                    }
                }
            } else if (checkValid(itemStack)) {
                if (itemStack.getComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1) < 16) {
                    ComponentMap itemStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, 16).build();
                    itemStack.applyComponentsFrom(itemStackComponent);
                }
                i = builder.add(itemStack);
                if (i > 0) {
                    super.playInsertSound(player);
                }
                slot.setStack(ItemStack.EMPTY);
            }
            stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
            return true;
        }

        @Override
        public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
            if (clickType != ClickType.RIGHT || !slot.canTakePartial(player)) {
                return false;
            }
            BundleContentsComponent bundleContentsComponent = stack.get(DataComponentTypes.BUNDLE_CONTENTS);
            if (bundleContentsComponent == null) {
                return false;
            }
            BundleContentsComponent.Builder builder = new BundleContentsComponent.Builder(bundleContentsComponent);
            if (otherStack.isEmpty()) {
                ItemStack itemStack = builder.removeFirst();
                if (itemStack != null) {
                    super.playRemoveOneSound(player);
                    if (itemStack.getCount() > 1 && itemStack.getDefaultComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1) < 16) {
                        ItemStack itemStack2 = itemStack.split(1);
                        ComponentMap itemStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, itemStack2.getDefaultComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1)).build();
                        itemStack2.applyComponentsFrom(itemStackComponent);
                        cursorStackReference.set(itemStack2);
                        builder.add(itemStack);
                    } else {
                        ComponentMap itemStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, itemStack.getDefaultComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1)).build();
                        itemStack.applyComponentsFrom(itemStackComponent);
                        cursorStackReference.set(itemStack);
                    }
                }
            } else if (checkValid(otherStack)) {
                if (otherStack.getComponents().getOrDefault(DataComponentTypes.MAX_STACK_SIZE, 1) < 16) {
                    ComponentMap otherStackComponent = ComponentMap.builder().add(DataComponentTypes.MAX_STACK_SIZE, 16).build();
                    otherStack.applyComponentsFrom(otherStackComponent);
                }
                int i = builder.add(otherStack);
                if (i > 0) {
                    super.playInsertSound(player);
                }
            }
            stack.set(DataComponentTypes.BUNDLE_CONTENTS, builder.build());
            return true;
        }
    }


    public static class BuildersBundleItem extends BundleBrillianceItem {
        public BuildersBundleItem() {
            super();
        }

        @Override
        public boolean checkValid(ItemStack insertStack) {
            //TODO: Replace with config
            List<TagKey<Item>> tags = List.of(
                    ConventionalItemTags.STONES,
                    ConventionalItemTags.BRICKS,
                    ConventionalItemTags.NETHER_BRICKS,
                    ConventionalItemTags.BARRELS,
                    ConventionalItemTags.CONCRETES,
                    ConventionalItemTags.DYED,
                    ConventionalItemTags.GLASS_BLOCKS,
                    ItemTags.PLANKS,
                    ItemTags.LOGS,
                    ItemTags.SAND,
                    ItemTags.WOOL,
                    ItemTags.SOUL_FIRE_BASE_BLOCKS
            );

            for (TagKey<Item> tag : tags) {
                if (TagUtil.isIn(tag, insertStack.getItem())) {
                    return true;
                }
            }
            return false;
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

    private void playRemoveOneSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }

    private void playInsertSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_INSERT, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.ITEM_BUNDLE_DROP_CONTENTS, 0.8f, 0.8f + entity.getWorld().getRandom().nextFloat() * 0.4f);
    }
}
