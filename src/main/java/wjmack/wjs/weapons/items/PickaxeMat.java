package wjmack.wjs.weapons.items;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PickaxeMat implements ToolMaterial {

    @Override
    public float getAttackDamage() {

        return 3f;
    }

    @Override
    public int getDurability() {

        return 2048;
    }

    @Override
    public int getEnchantability() {

        return 15;
    }

    @Override
    public int getMiningLevel() {

        return 4;
    }

    @Override
    public float getMiningSpeedMultiplier() {

        return 20.0f;
    }

    @Override
    public Ingredient getRepairIngredient() {

        return Ingredient.ofItems(Items.AMETHYST_SHARD);
    }

}