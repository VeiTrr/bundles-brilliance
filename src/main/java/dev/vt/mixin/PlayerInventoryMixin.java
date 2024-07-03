package dev.vt.mixin;


import dev.vt.items.BundleBrillianceItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Final
    @Shadow
    public DefaultedList<ItemStack> main;

    @Shadow
    protected abstract int addStack(ItemStack stack);

    @Shadow
    @Final
    public PlayerEntity player;

    @Inject(method = "insertStack(ILnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerInventory;addStack(Lnet/minecraft/item/ItemStack;)I"
            ),
            cancellable = true)
    private void insertStack(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == -1) {
            for (ItemStack itemStack : this.main) {
                if (itemStack.getItem() instanceof BundleBrillianceItem bundleItem) {
                    if (bundleItem.insertStack(itemStack, stack, this.player)) {
                        cir.setReturnValue(true);
                        return;
                    }
                }
            }
        }
    }
}
