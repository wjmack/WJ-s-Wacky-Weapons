package wjmack.wjs.weapons.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import wjmack.wjs.weapons.access.PlayerEntityMixinAccessor;

import java.util.List;

public class HawkingWatch extends Item {

    public HawkingWatch(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.wjsweapons.hw_subtext_1").formatted(Formatting.GRAY));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ((PlayerEntityMixinAccessor) user).rewindTime();
        user.getItemCooldownManager().set(this, 200);

        return TypedActionResult.success(user.getStackInHand(hand));
    }

}
