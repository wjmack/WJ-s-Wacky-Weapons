package wjmack.wjs.weapons.items;

import wjmack.wjs.weapons.WJsWackyWeapons;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Atomizer extends SwordItem {
    public Atomizer(ToolMaterial toolMaterial) {
        super(toolMaterial, 11, -3.25f, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new TranslatableText("item.wjsweapons.at_subtext_1").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("item.wjsweapons.at_subtext_2").formatted(Formatting.GRAY));
        tooltip.add(new TranslatableText("item.wjsweapons.at_subtext_3").formatted(Formatting.GRAY));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity user) {
        entity.playSound(WJsWackyWeapons.BONK, 1.0F, 1.0F);
        double userVelX = user.getRotationVec(0).x;
        double userVelZ = user.getRotationVec(0).z;
        double modifier = 2;
        if (user.isSneaking())
            modifier = 5;
        entity.addVelocity(modifier * userVelX, modifier * 0.2, modifier * userVelZ);
        return super.postHit(stack, entity, user);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        if (playerEntity.isOnGround()) {
            playerEntity.getItemCooldownManager().set(this, 60);
            playerEntity.addEnchantedHitParticles(playerEntity);
            playerEntity.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE, 1.0F, 1.0F);
            double userVelX = playerEntity.getRotationVec(0).x;
            double userVelZ = playerEntity.getRotationVec(0).z;
            playerEntity.addVelocity(8 * userVelX, 0, 8 * userVelZ);
        }
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }

}
