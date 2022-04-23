package wjmack.wjs.weapons.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import wjmack.wjs.weapons.WJsWackyWeapons;

public class CanaanBlock extends Block {

    public CanaanBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {

        if (!world.isClient) {
            world.playSound(null, pos, WJsWackyWeapons.CANAAN, SoundCategory.BLOCKS, 10.0F, 1.0F);
        }

        return ActionResult.SUCCESS;
    }
}
