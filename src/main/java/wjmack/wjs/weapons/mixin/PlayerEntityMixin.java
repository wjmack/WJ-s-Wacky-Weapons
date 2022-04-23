package wjmack.wjs.weapons.mixin;

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

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityMixinAccessor {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    int soundTimer = 0;

    float defaultAirSpeed = this.airStrafingSpeed;

    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo info) {

        ItemStack helmetStack = this.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack bootsStack = this.getEquippedStack(EquipmentSlot.FEET);

        this.setNoGravity(helmetStack.getItem().equals(WJsWackyWeapons.SPACE_HELMET) && !this.isFallFlying());

        soundTimer++;
        if (soundTimer >= 1) {
            soundTimer = 0;
        }

        if (this.hasNoGravity()) {
            this.addVelocity(0, (this.isSneaking() ? -0.03 : -0.016), 0);
        }
        if (this.jumping && bootsStack.getItem().equals(WJsWackyWeapons.ROCKET_BOOTS) && !this.isFallFlying()) {
            this.setNoGravity(true);
            if (soundTimer == 0) {
                this.playSound(WJsWackyWeapons.ROCKET, 0.2F, 1.0F);
            }
            double upSpeed = this.isSneaking() ? 0.22 : 0.45;
            this.addVelocity(0, 0.045, 0);
            if (this.getVelocity().y > upSpeed) {
                this.setVelocity(this.getVelocity().x, upSpeed, this.getVelocity().z);
            }
            world.addParticle(ParticleTypes.FLAME, (double) this.getX(),
                    (double) this.getY(), (double) this.getZ(), 0.0, -0.5, 0.0);

        }

        this.airStrafingSpeed = (float) (bootsStack.getItem() == WJsWackyWeapons.ROCKET_BOOTS
                ? 2.2 * defaultAirSpeed
                : defaultAirSpeed);
    }
}