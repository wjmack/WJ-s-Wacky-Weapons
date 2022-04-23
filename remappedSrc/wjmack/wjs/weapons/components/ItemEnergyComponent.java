package wjmack.wjs.weapons.components;

import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import net.minecraft.item.ItemStack;

public class ItemEnergyComponent extends ItemComponent implements EnergyComponent {

    public ItemEnergyComponent(ItemStack stack) {
        super(stack);
    }

    private int energy = 100;

    public int getEnergyValue() {
        return this.energy;
    }

    public void setEnergyValue(int value) {
        this.energy = value;
    }

    public void addToEnergy(int amount) {
        this.energy += amount;
    }

    public void subtractFromEnergy(int amount) {
        this.energy -= amount;
    }

}