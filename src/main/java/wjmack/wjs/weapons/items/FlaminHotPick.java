package wjmack.wjs.weapons.items;

import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.text.Text;

public class FlaminHotPick extends PickaxeItem {

    public FlaminHotPick(ToolMaterial toolMaterial) {
        super(toolMaterial, 2, -2.2f, new Item.Settings().group(ItemGroup.TOOLS));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.wjsweapons.fhp_subtext_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.fhp_subtext_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.fhp_subtext_3").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.fhp_subtext_4").formatted(Formatting.GRAY));
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity entity, LivingEntity user) {
        entity.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);
        entity.setOnFireFor(5);
        return super.postHit(stack, entity, user);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0));
        playerEntity.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0f, 1.0f);
        playerEntity.getItemCooldownManager().set(this, 400);

        return TypedActionResult.success(playerEntity.getStackInHand(hand));

    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        int chance = ThreadLocalRandom.current().nextInt(1, 1024 + 1);
        int howMany = ThreadLocalRandom.current().nextInt(1, 3 + 1);
        if (chance == 69) {
            ((PlayerEntity) miner).giveItemStack(new ItemStack(Items.DIAMOND, howMany));
            ((PlayerEntity) miner)
                    .sendMessage(
                            Text.literal(
                                    "That block dropped " + howMany + " diamond" + (howMany == 1 ? "." : "s.")),
                            true);
            miner.playSound(SoundEvents.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
        }
        return super.postMine(stack, world, state, pos, miner);
    }

}
