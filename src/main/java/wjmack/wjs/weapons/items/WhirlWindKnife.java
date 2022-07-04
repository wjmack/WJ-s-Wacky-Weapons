package wjmack.wjs.weapons.items;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.text.Text;

public class WhirlWindKnife extends SwordItem {
    public WhirlWindKnife(ToolMaterial toolMaterial) {
        super(toolMaterial, 2, -1.5f, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.wjsweapons.knife_subtext_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.knife_subtext_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.knife_subtext_3").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.knife_subtext_4").formatted(Formatting.GRAY));
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.isSneaking() && !user.getItemCooldownManager().isCoolingDown(this)) {
            entity.addVelocity(0, 2, 0);
            user.addCritParticles(entity);
            entity.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1.0F, 1.0F);
            user.getItemCooldownManager().set(this, 60);
        }
        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack knife = playerEntity.getStackInHand(hand);
        double userVelX = playerEntity.getRotationVec(0).x;
        double userVelY = playerEntity.getRotationVec(0).y;
        double userVelZ = playerEntity.getRotationVec(0).z;
        playerEntity.playSound(SoundEvents.ITEM_TRIDENT_THROW, 1.0F, 1.0F);
        playerEntity.addCritParticles(playerEntity);
        double vel = playerEntity.isSneaking() ? 3 : 2;
        if (playerEntity.isOnGround() || playerEntity.isInsideWaterOrBubbleColumn()) {
            playerEntity.getItemCooldownManager().set(this, playerEntity.isSneaking() ? 140 : 90);
            playerEntity.addVelocity(vel * userVelX, (vel - 0.5) * userVelY, vel * userVelZ);
        } else {
            playerEntity.getItemCooldownManager().set(this, 140);
            playerEntity.addVelocity(1.6 * userVelX, 2.1 * userVelY, 1.6 * userVelZ);
        }
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
