package biomesoplenty.block;

import biomesoplenty.init.ModParticles;
import biomesoplenty.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PusBubbleBlock extends Block
{
    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D);

    public PusBubbleBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext selectionContext)
    {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos)
    {
        // Check if the block below is suitable for placing the Pus Bubble Block
        // pus can only be placed on flesh decoration placeable blocks which is defined in ModTags.
        BlockState groundState = worldIn.getBlockState(pos.below());
        return groundState.is(ModTags.Blocks.FLESH_DECORATION_PLACEABLE) && groundState.isFaceSturdy(worldIn, pos.below(), Direction.UP);
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random)
    {
        // If the block can no longer survive, replace it with air, otherwise proceed with the default update shape behavior basically
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, tickAccess, pos, facing, facingPos, facingState, random);
    }

    @Override
    public void onProjectileHit(Level p_57381_, BlockState p_57382_, BlockHitResult p_57383_, Projectile p_57384_)
    {
        // When hit by a projectile, destroy the block and spawn particles, it will not drop as an item, and any projectile will trigger this
        //particles are for gross effect
        p_57381_.destroyBlock(p_57383_.getBlockPos(), false);
        this.spawnParticles(p_57381_, p_57383_.getBlockPos());
    }

    @Override
    public void attack(BlockState p_55467_, Level p_55468_, BlockPos p_55469_, Player p_55470_)
    {
        // When attacked by a player, destroy the block and spawn particles
        // similar to onProjectileHit but for player attacks
        this.spawnParticles(p_55468_, p_55469_);
        super.attack(p_55467_, p_55468_, p_55469_, p_55470_);
    }

    @Override
    public void wasExploded(ServerLevel p_54184_, BlockPos p_54185_, Explosion p_54186_)
    {
        // When exploded, spawn particles
        // this is for when the block is destroyed by an explosion
        if (p_54184_ instanceof ServerLevel)
        {
            this.spawnParticles((ServerLevel)p_54184_, p_54185_);
        }
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean b)
    {
        // When an entity is inside the block, destroy the block and spawn particles this is when a living entity walks into the block.
        // this is for gross effect as well.
        if (entity instanceof LivingEntity)
        {
            level.destroyBlock(pos, false);
            spawnParticles(level, pos);
        }
    }

    public static void spawnParticles(Level p_55480_, BlockPos pos)
    {
        // Spawn pus particles at the given position, this is for particle effect when the block is destroyed, gross pus effect.
        RandomSource rand = p_55480_.random;
        for (int i = 0; i < 10; i++)
        {
            // p_55480_.addParticle(ModParticles.PUS, (double)(pos.getX() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 8.0D)), (double) (pos.getY() + 0.25D), (double) (pos.getZ() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 8.0D)), 0.0D, 0.0D, 0.0D);
            p_55480_.addParticle(
                ModParticles.PUS,
                (double)(pos.getX() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 8.0D)),  // X: center of block ± small random offset (±0.125)
                (double)(pos.getY() + 0.25D),                                                   // Y: slightly above block bottom (¼ block height)
                (double)(pos.getZ() + 0.5D + ((rand.nextDouble() - rand.nextDouble()) / 8.0D)), // Z: center of block ± small random offset (±0.125)
                0.0D,  // X velocity (particle stationary)
                0.0D,  // Y velocity
                0.0D   // Z velocity
            );
        }
        
    }

    @Override
    public boolean canBeReplaced(BlockState p_53910_, BlockPlaceContext p_53911_)
    {
        // yes, allow the block to be replaced when placing another block on it. treated like plants
        return true;
    }
}