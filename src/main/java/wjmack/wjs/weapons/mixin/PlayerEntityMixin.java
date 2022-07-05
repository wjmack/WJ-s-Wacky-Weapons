package wjmack.wjs.weapons.mixin;

import net.minecraft.entity.player.PlayerInventory;
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

    private double[][] positions = new double[60][4];
    private double[][] telePositions = new double[60][3];
    private boolean rewindingTime = false;
    private int startIndex = 0;
    private int rewindEndPoint;


    @Inject(method = "tick()V", at = @At("TAIL"))
    private void tick(CallbackInfo info) {
        globalTimer++;
        rocketBootsCheck();
        spaceHelmetCheck();
        saveWatchPositions();
        doRewind();
    }

    private void spaceHelmetCheck() {
        ItemStack helmetStack = this.getEquippedStack(EquipmentSlot.HEAD);
        this.setNoGravity(helmetStack.getItem().equals(WJsWackyWeapons.SPACE_HELMET) && !this.isFallFlying());
        if (this.hasNoGravity()) {
            this.addVelocity(0, (this.isSneaking() ? -0.03 : -0.016), 0);
        }
    }

    private void rocketBootsCheck() {
        ItemStack bootsStack = this.getEquippedStack(EquipmentSlot.FEET);
        Set<Item> flightSet = new HashSet<>();
        flightSet.add(Items.COAL);

        if (this.jumping && bootsStack.getItem().equals(WJsWackyWeapons.ROCKET_BOOTS) && !this.isFallFlying() && ((PlayerEntity) (Object) this).getInventory().containsAny(flightSet)) {
            this.setNoGravity(true);
            if (globalTimer % 2 == 0) {
                this.playSound(WJsWackyWeapons.ROCKET, 0.2F, 1.0F);
            }
            double upSpeed = this.isSneaking() ? 0.22 : 0.45;
            this.addVelocity(0, upSpeed/3, 0);
            if (this.getVelocity().y > upSpeed) {
                this.setVelocity(this.getVelocity().x, upSpeed, this.getVelocity().z);
            }
            world.addParticle(ParticleTypes.FLAME, (double) this.getX(),
                    (double) this.getY(), (double) this.getZ(), 0.0, -0.5, 0.0);

            if (globalTimer % 10 == 0) {
                PlayerInventory playerInventory = ((PlayerEntity) (Object) this).getInventory();
                int slot = playerInventory.getSlotWithStack(new ItemStack(Items.COAL));
            }

            this.fallDistance = 0;

            this.airStrafingSpeed = (float) (bootsStack.getItem() == WJsWackyWeapons.ROCKET_BOOTS
                    ? 2.2 * defaultAirSpeed
                    : defaultAirSpeed);
        }
    }

    private void saveWatchPositions() {
        if(rewindingTime || globalTimer < 60) return;
        int index = globalTimer % 60;
        positions[index][0] = this.getX();
        positions[index][1] = this.getY();
        positions[index][2] = this.getZ();
        positions[index][3] = 1;
        if(index == 0) return;
        positions[index-1][3] = 0;
    }

    public void rewindTime() {
        if(!rewindingTime) {
            rewindingTime = true;
            rewindEndPoint = globalTimer + 60;
        }
        for(int i = 0; i < 59; i++) {
            if(positions[i][3] == 1) {
                startIndex = i;
            }
        }
        for(int i = 0; i < 59; i++) {
           telePositions[i][0] = positions[(i + startIndex) % 60][0];
           telePositions[i][1] = positions[(i + startIndex) % 60][1];
           telePositions[i][2] = positions[(i + startIndex) % 60][2];
        }
    }

    private void doRewind() {
        if(globalTimer < rewindEndPoint && rewindingTime) {
            int index = (59 - (globalTimer % 60) + startIndex) % 60;
            if(index != 0)
                this.teleport(telePositions[index][0],telePositions[index][1],telePositions[index][2]);
        }
        if(globalTimer > rewindEndPoint) {
            rewindingTime = false;
        }
    }

    public boolean isJumping() {
        return this.jumping;
    }
}