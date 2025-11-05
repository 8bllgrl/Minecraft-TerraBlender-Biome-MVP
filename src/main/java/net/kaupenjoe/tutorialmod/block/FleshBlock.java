package net.kaupenjoe.tutorialmod.block;

import biomesoplenty.api.block.BOPFluids;
import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.kaupenjoe.tutorialmod.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class FleshBlock extends Block
{
    public FleshBlock(Block.Properties properties)
    {
        // block properties passed from minecraft block init class
        super(properties);
    }

    @Override
    public void stepOn(Level worldIn, BlockPos pos, BlockState blockState, Entity entityIn)
    {
        //slows down entities walking on it
        entityIn.setDeltaMovement(entityIn.getDeltaMovement().multiply(0.95D, 1.0D, 0.95D));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource p_221787_)
    {
        // this method is for random block ticks, like plant growth.
        boolean bloodAdjacent = false;
        for (Direction direction : Direction.values())
        {
            //if there is blood fluid adjacent to the flesh block
            if (worldIn.getFluidState(pos.relative(direction)).is(BOPFluids.BLOOD))
            {
                //used later for plant growth
                bloodAdjacent = true;
            }
        }

        // this is for growing plants on top of the flesh block when blood is adjacent
        //basically this implies the blood is nourishing the flesh block to grow these plants
        if (bloodAdjacent && p_221787_.nextInt(15) == 0)
        {
            switch (p_221787_.nextInt(4))
            {
                default:
                case 0:
                    if (worldIn.isEmptyBlock(pos.above()))
                    {
                        //this places hair block on top of flesh block
                        worldIn.setBlock(pos.above(), BOPBlocks.HAIR.defaultBlockState(), 2);
                    }
                    break;

                case 1:
                    if (worldIn.isEmptyBlock(pos.above()))
                    {
                        //pus bubble block on top of flesh block
                        worldIn.setBlock(pos.above(), BOPBlocks.PUS_BUBBLE.defaultBlockState(), 2);
                    }
                    break;

                case 2:
                    if (worldIn.isEmptyBlock(pos.above()) && worldIn.isEmptyBlock(pos.above(2)))
                    {
                        //eyebulb double plant on top of flesh block
                        DoublePlantBlock.placeAt(worldIn, BOPBlocks.EYEBULB.defaultBlockState(), pos.above(), 2);
                    }
                    break;

                case 3:
                    if (worldIn.isEmptyBlock(pos.below()))
                    {
                        //flesh tendons block below flesh block
                        worldIn.setBlock(pos.below(), BOPBlocks.FLESH_TENDONS.defaultBlockState(), 2);
                    }
                    break;
            }
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult)
    {
        if (stack.is(ModTags.Items.SHEARS) && state.getBlock() == BOPBlocks.FLESH)
        {
            //wait, why isn't this handled elsewhere? seems odd and repetitive to have it here. espeically the velocity thing, oh well.
            //this is for shearing the flesh block to get porous flesh and rotten flesh item
            if (!level.isClientSide())
            {
                //get the direction the player is facing and adjust item spawn accordingly
                Direction direction = hitResult.getDirection();
                Direction direction1 = direction.getAxis() == Direction.Axis.Y ? player.getDirection().getOpposite() : direction;
                //spawn sound, change block, spawn item entity, damage shears, trigger game event and stat
                level.playSound((Player)null, pos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
                // default block state of porous flesh block
                level.setBlock(pos, BOPBlocks.POROUS_FLESH.defaultBlockState(), 11);
                // then spawns rotten flesh item
                ItemEntity itementity = new ItemEntity(level, (double)pos.getX() + 0.5D + (double)direction1.getStepX() * 0.65D, (double)pos.getY() + 0.1D, (double)pos.getZ() + 0.5D + (double)direction1.getStepZ() * 0.65D, new ItemStack(Items.ROTTEN_FLESH, 1));
                // gives the item a slight velocity away from the block
                itementity.setDeltaMovement(0.05D * (double)direction1.getStepX() + level.random.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.getStepZ() + level.random.nextDouble() * 0.02D);
                // adds the item entity to the world
                level.addFreshEntity(itementity);
                stack.hurtAndBreak(1, player, hand);
                // trigger shear game event and stat
                level.gameEvent(player, GameEvent.SHEAR, pos);
                player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
            }
            return InteractionResult.SUCCESS;
        }
        else
        {
            //this is the default action if not shearing flesh block
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }
    }
}