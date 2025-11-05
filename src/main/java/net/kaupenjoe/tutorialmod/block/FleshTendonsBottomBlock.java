package net.kaupenjoe.tutorialmod.block;

import biomesoplenty.init.ModParticles;
import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.kaupenjoe.tutorialmod.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FleshTendonsBottomBlock extends HangingStrandBottomBlock {

    public FleshTendonsBottomBlock(Properties p_i241195_1_) {
        super(p_i241195_1_);
    }

    @Override
    protected Block getBodyBlock() {
        return BOPBlocks.FLESH_TENDONS_STRAND;
    }

    @Override
    public boolean canSurvive(BlockState p_196260_1_, LevelReader p_196260_2_, BlockPos p_196260_3_) {
        // check if the block can survive by checking the block it is attached to
        // similar to FleshTendonsBlock but for the bottom block
        BlockPos blockpos = p_196260_3_.relative(this.growthDirection.getOpposite());
        BlockState blockstate = p_196260_2_.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return block == this.getHeadBlock() || block == this.getBodyBlock() || blockstate.is(ModTags.Blocks.FLESH);
        }
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand)
    {
        // add dripping blood particle effect
        // that is why it is named animateTick
        super.animateTick(stateIn, worldIn, pos, rand);
        if (rand.nextInt(7) == 0)
        {
            // Add dripping blood particle effect with randomness
            // worldIn.addAlwaysVisibleParticle(ModParticles.DRIPPING_BLOOD, (double) (pos.getX() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 2.0D)), (double) (pos.getY() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 2.0D)), (double) (pos.getZ() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 2.0D)), 0.0D, 0.0D, 0.0D);
            worldIn.addAlwaysVisibleParticle(
                ModParticles.DRIPPING_BLOOD,
                (double)(pos.getX() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 2.0D)),  // X: center of block ± small random offset
                (double)(pos.getY() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 2.0D)),  // Y: center of block ± small random offset
                (double)(pos.getZ() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 2.0D)),  // Z: center of block ± small random offset
                0.0D,  // X velocity (particle stationary)
                0.0D,  // Y velocity
                0.0D   // Z velocity
            );
        }
    }
}