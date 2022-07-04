package wjmack.wjs.weapons.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import wjmack.wjs.weapons.WJsWackyWeapons;
import wjmack.wjs.weapons.access.PlayerEntityMixinAccessor;

import java.util.HashSet;
import java.util.Set;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityMixinAccessor {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    int globalTimer = 0;
    float defaultAirSpeed = this.airStrafingSpeed;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        ItemStack helmetStack = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack bootsStack = this.getEquippedStack(EquipmentSlot.FEET);

        this.setNoGravity(helmetStack.getItem().equals(WJsWackyWeapons.SPACE_HELMET) && !this.isFallFlying());

        globalTimer++;

        if (this.hasNoGravity()) {
            this.addVelocity(0, (this.isSneaking() ? -0.03 : -0.016), 0);
        }

        Set<Item> flightSet = new HashSet<Item>();
        flightSet.add(Items.COAL);
        if (this.jumping && bootsStack.getItem().equals(WJsWackyWeapons.ROCKET_BOOTS) && !this.isFallFlying() && ((PlayerEntity) (Object) this).getInventory().containsAny(flightSet)) {
            this.setNoGravity(true);
            if (globalTimer % 2 == 0) {
                this.playSound(WJsWackyWeapons.ROCKET, 0.2F, 1.0F);
            }
            double upSpeed = this.isSneaking() ? 0.22 : 0.45;
            this.addVelocity(0, 0.045, 0);
            if (this.getVelocity().y > upSpeed) {
                this.setVelocity(this.getVelocity().x, upSpeed, this.getVelocity().z);
            }
            world.addParticle(ParticleTypes.FLAME, (double) this.getX(),
                    (double) this.getY(), (double) this.getZ(), 0.0, -0.5, 0.0);

            if(globalTimer % 10 == 0) {
                PlayerInventory playerInventory = ((PlayerEntity) (Object) this).getInventory();
                int slot = playerInventory.getSlotWithStack(new ItemStack(Items.COAL));
                playerInventory.removeStack(slot, 1);
            }

            this.fallDistance = 0;


        }

        this.airStrafingSpeed = (float) (bootsStack.getItem() == WJsWackyWeapons.ROCKET_BOOTS
                ? 2.2 * defaultAirSpeed
                : defaultAirSpeed);
    }
}