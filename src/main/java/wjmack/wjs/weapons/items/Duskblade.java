package wjmack.wjs.weapons.items;

import java.util.List;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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

public class Duskblade extends SwordItem {
    public Duskblade(ToolMaterial toolMaterial) {
        super(toolMaterial, 4, -2.8f, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.wjsweapons.db_subtext_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.db_subtext_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.db_subtext_3").formatted(Formatting.GRAY));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity user) {
        if (user.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            entity.addStatusEffect(new StatusEffectInstance(
                    !entity.isUndead() ? StatusEffects.INSTANT_DAMAGE : StatusEffects.INSTANT_HEALTH, 1, 1));
            ((PlayerEntity) user).addEnchantedHitParticles(entity);
        }
        if (entity.isDead()) {
            if (!user.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                entity.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, 2.0F, 1.0F);
            }
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600, 1));
            ((PlayerEntity) user).addCritParticles(user);
        }
        return super.postHit(stack, entity, user);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            user.removeStatusEffect(StatusEffects.INVISIBILITY);
            user.playSound(SoundEvents.BLOCK_RESPAWN_ANCHOR_AMBIENT, 2.0F, 1.0F);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
