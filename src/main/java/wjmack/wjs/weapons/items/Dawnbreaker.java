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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import wjmack.wjs.weapons.armor.GuardHelmet;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.text.Text;

public class Dawnbreaker extends SwordItem {
    public Dawnbreaker(ToolMaterial toolMaterial) {
        super(toolMaterial, 5, -2.4f, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.wjsweapons.dw_subtext_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.dw_subtext_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.dw_subtext_3").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.dw_subtext_4").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.dw_subtext_5").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.dw_subtext_6").formatted(Formatting.GRAY));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity user) {
        boolean isWearingHelmet = false;
        for (ItemStack fun : user.getArmorItems()) {
            if (fun.getItem() instanceof GuardHelmet) {
                isWearingHelmet = true;
            }
        }
        if (entity.isUndead() || isWearingHelmet) {
            entity.addStatusEffect(new StatusEffectInstance(
                    entity.isUndead() ? StatusEffects.INSTANT_HEALTH : StatusEffects.INSTANT_DAMAGE, 1, 1));
            entity.playSound(SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, 2.0F, 1.0F);
            ((PlayerEntity) user).addEnchantedHitParticles(entity);
        }

        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 1200, 1));
        return super.postHit(stack, entity, user);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            entity.setOnFireFor(5);
            user.addCritParticles(entity);
            entity.playSound(SoundEvents.ENTITY_ENDER_DRAGON_SHOOT, 1.0F, 1.0F);
            user.getItemCooldownManager().set(this, 60);
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

}
