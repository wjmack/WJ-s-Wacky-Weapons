package wjmack.wjs.weapons.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;
import net.minecraft.util.Identifier;
import wjmack.wjs.weapons.WJsWackyWeapons;

public class InitComponent implements ItemComponentInitializer {
    public static final ComponentKey<EnergyComponent> ENERGY = ComponentRegistry
            .getOrCreate(new Identifier("wjsweapons", "energy"), EnergyComponent.class);

    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(WJsWackyWeapons.WHIRLWIND_KNIFE, ENERGY, ItemEnergyComponent::new);
    }

}