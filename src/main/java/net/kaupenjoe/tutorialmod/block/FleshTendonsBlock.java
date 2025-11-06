package net.kaupenjoe.tutorialmod.block;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.init.ModTags;
import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.kaupenjoe.tutorialmod.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FleshTendonsBlock extends HangingStrandBlock
{
    public FleshTendonsBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock()
    {
        // growing plant head block, which is the basis for this block
        return (GrowingPlantHeadBlock) BOPBlocks.FLESH_TENDONS;
    }

    @Override
    public boolean canSurvive(BlockState p_196260_1_, LevelReader p_196260_2_, BlockPos p_196260_3_) {
        // check if the block can survive by checking the block it is attached to
        // this helps to grow the block 
        //get opposite direction of growth is down. i think
        BlockPos blockpos = p_196260_3_.relative(this.growthDirection.getOpposite());
        BlockState blockstate = p_196260_2_.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        // check if the block can attach to the block below it
        if (!this.canAttachTo(blockstate)) {
            // if it can't attach, return false
            return false;
        } else {
            // otherwise, check if the block is the head or body block or is tagged as flesh return true if it is
            // this is so that the block can grow on itself and other flesh blocks. allows for more organic growth patterns and so that tendons can actually attach to flesh blocks naturally.
            return block == this.getHeadBlock() || block == this.getBodyBlock() || blockstate.is(ModTags.Blocks.FLESH);
        }
    }
}