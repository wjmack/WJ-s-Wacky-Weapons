package wjmack.wjs.weapons.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import wjmack.wjs.weapons.WJsWackyWeapons;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wjmack.wjs.weapons.access.PlayerEntityMixinAccessor;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow
    public ServerPlayNetworkHandler networkHandler;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        // Instance of the player/world/window handle
        ServerPlayerEntity player = this.networkHandler.player;

        // Stacks for checking purposes
        ItemStack equippedStack = player.getEquippedStack(EquipmentSlot.MAINHAND);
        ItemStack feetStack = player.getEquippedStack(EquipmentSlot.FEET);

        if (player.hasNoGravity() || equippedStack.getItem().equals(WJsWackyWeapons.WHIRLWIND_KNIFE) || feetStack.getItem().equals(WJsWackyWeapons.ROCKET_BOOTS)) {
            player.handleFall(0, true);
        }
    }
}