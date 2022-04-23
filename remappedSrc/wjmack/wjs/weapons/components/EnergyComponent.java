package wjmack.wjs.weapons.components;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;

public interface EnergyComponent extends ComponentV3 {

    public int getEnergyValue();

    public void setEnergyValue(int value);

    public void addToEnergy(int amount);

    public void subtractFromEnergy(int amount);

    @Override
    public void readFromNbt(NbtCompound tag);

    @Override
    public void writeToNbt(NbtCompound tag);

}