package wjmack.wjs.weapons;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import wjmack.wjs.weapons.items.*;
import wjmack.wjs.weapons.blocks.*;
import wjmack.wjs.weapons.armor.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WJsWackyWeapons implements ModInitializer {
	public static final ToolItem WHIRLWIND_KNIFE = new WhirlWindKnife(new WeaponsMat());
	public static final ToolItem ATOMIZER = new Atomizer(new WeaponsMat());
	public static final ToolItem DAWNBREAKER = new Dawnbreaker(new WeaponsMat());
	public static final ToolItem DUSKBLADE = new Duskblade(new WeaponsMat());
	public static final ToolItem FLAMIN_HOT_PICKAXE = new FlaminHotPick(new PickaxeMat());
	public static final ArmorItem GUARD_HELMET = new GuardHelmet(new GuardMat(), EquipmentSlot.HEAD);
	public static final ArmorItem SPACE_HELMET = new SpaceHelmet(new SpaceMat(), EquipmentSlot.HEAD);
	public static final ArmorItem ROCKET_BOOTS = new RocketBoots(new SpaceMat(), EquipmentSlot.FEET);
	public static final String MOD_ID = "wjsweapons";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static final Identifier BONK_ID = new Identifier("wjsweapons:bonk");
	public static final SoundEvent BONK = new SoundEvent(BONK_ID);

	public static final Identifier CANAAN_ID = new Identifier("wjsweapons:canaan");
	public static final SoundEvent CANAAN = new SoundEvent(CANAAN_ID);

	public static final Identifier ROCKET_ID = new Identifier("wjsweapons:rocket");
	public static final SoundEvent ROCKET = new SoundEvent(ROCKET_ID);

	public static final Block CANAAN_BLOCK = new CanaanBlock(
			net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.of(Material.SOLID_ORGANIC)
					.strength(0.5f, 0.5f).sounds(BlockSoundGroup.GRAVEL).ticksRandomly());


	@Override
	public void onInitialize() {

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "whirlwind_knife"), WHIRLWIND_KNIFE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "atomizer"), ATOMIZER);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "dawnbreaker"), DAWNBREAKER);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "duskblade"), DUSKBLADE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "flamin_hot_pickaxe"), FLAMIN_HOT_PICKAXE);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "canaan_block"),
				new BlockItem(CANAAN_BLOCK, new Item.Settings().group(ItemGroup.DECORATIONS)));

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "canaan_block"), CANAAN_BLOCK);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "guard_helmet"), GUARD_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "space_helmet"), SPACE_HELMET);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "rocket_boots"), ROCKET_BOOTS);

		Registry.register(Registry.SOUND_EVENT, WJsWackyWeapons.BONK_ID, BONK);
		Registry.register(Registry.SOUND_EVENT, WJsWackyWeapons.ROCKET_ID, ROCKET);
		Registry.register(Registry.SOUND_EVENT, WJsWackyWeapons.CANAAN_ID, CANAAN);

		ClientPlayNetworking.registerGlobalReceiver(new Identifier("wjswackyweapons", "rocket_boots_packet"),
				(client, handler, buf, responseSender) -> {
					BlockPos player = buf.readBlockPos();

					client.execute(() -> {
						client.world.addParticle(ParticleTypes.FLAME, player.getX(), player.getY(), player.getZ(), 0F,
								-0.5F, 0F);
					});
				});

		LOGGER.info("Hello Fabric world!");

	}
}
