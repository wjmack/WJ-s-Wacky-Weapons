package wjmack.wjs.weapons.armor;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class RocketBoots extends ArmorItem {
    public RocketBoots(ArmorMaterial material, EquipmentSlot slot) {
        super(material, slot, new Item.Settings().group(ItemGroup.COMBAT));
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.wjsweapons.rb_subtext_1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.rb_subtext_2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.wjsweapons.rb_subtext_3").formatted(Formatting.GRAY));
    }
}