package net.kaupenjoe.tutorialmod.block;

import net.kaupenjoe.tutorialmod.api.block.BOPFluids;
import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.kaupenjoe.tutorialmod.api.item.BOPItems;
import net.kaupenjoe.tutorialmod.api.sound.BOPSounds;
import net.kaupenjoe.tutorialmod.init.ModFluidTypes;
import net.kaupenjoe.tutorialmod.init.ModParticles;
import net.kaupenjoe.tutorialmod.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.Optional;

//TODO: do all of the variable names need to be like p_76450_ etc.? is this to be compatible with forge or minecraft?
public abstract class BloodFluid extends FlowingFluid
{

    //gemini recc for non-mixins?
    @Override
    public FluidType getFluidType() // <-- Directly implement the required method
    {
        // Links to the FluidType object registered in your ModFluidTypes
        return ModFluidTypes.BLOOD_TYPE.get();
    }
    
    @Override
    public Fluid getFlowing() {
        //enum for assets?
        return BOPFluids.FLOWING_BLOOD;
    }

    @Override
    public Fluid getSource() {
        //enum for assets?
        return BOPFluids.BLOOD;
    }

    @Override
    public Item getBucket() {
        //enum for assets?
        return BOPItems.BLOOD_BUCKET;
    }

    @Override
    public void animateTick(Level p_230606_, BlockPos p_230607_, FluidState p_230608_, RandomSource p_230609_)
    {
        // this makes the sound of the blood fluid
        // "animate" seems to be the method that makes the particles and sound effects for fluids
        if (p_230609_.nextInt(256) == 0)
        {
            // plays the blood ambient sound at random intervals
            // randomness determined by nextInt(256) == 0 and the sound properties are set by the nextFloat() methods
            // p_230606_.playLocalSound((double)p_230607_.getX() + 0.5D, (double)p_230607_.getY() + 0.5D, (double)p_230607_.getZ() + 0.5D, BOPSounds.BLOOD_AMBIENT, SoundSource.BLOCKS, p_230609_.nextFloat() * 0.25F + 0.75F, p_230609_.nextFloat() + 0.5F, false);
            //
            // Plays a centered ambient sound at the block with slight random variation in volume (0.75–1.0) and pitch (0.5–1.5)
            p_230606_.playLocalSound(
                (double)p_230607_.getX() + 0.5D,   // X coordinate + 0.5 → centers the sound within the block
                (double)p_230607_.getY() + 0.5D,   // Y coordinate + 0.5 → same, centers vertically
                (double)p_230607_.getZ() + 0.5D,   // Z coordinate + 0.5 → centers in Z direction
                BOPSounds.BLOOD_AMBIENT,           // the sound to play
                SoundSource.BLOCKS,                // sound category (for volume control etc.)
                p_230609_.nextFloat() * 0.25F + 0.75F,  // volume: random between 0.75 and 1.0
                p_230609_.nextFloat() + 0.5F,           // pitch: random between 0.5 and 1.5
                false                               // don't delay or loop
            );
        }
    }

    @Nullable
    @Override
    public ParticleOptions getDripParticle()
    {
        //enum for assets?
        return ModParticles.DRIPPING_BLOOD;
    }

    @Override
    protected boolean canConvertToSource(ServerLevel p_256009_)
    {
        //enum for assets?
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor p_76450_, BlockPos p_76451_, BlockState p_76452_)
    {
        // drops the resources of the block being destroyed by the fluid
        // e.g. if water or fluid destroys a plant, the plant drops its items
        BlockEntity blockentity = p_76452_.hasBlockEntity() ? p_76450_.getBlockEntity(p_76451_) : null;
        Block.dropResources(p_76452_, p_76450_, p_76451_, blockentity);
    }

    @Override
    public int getSlopeFindDistance(LevelReader p_76464_) {
        // how far the fluid can spread horizontally
        return 3;
    }

    @Override
    public BlockState createLegacyBlock(FluidState p_76466_)
    {
        // converts the fluid state to a block state for placement in the world?
        // legacy level seems to refer to the fluid level (0-8)?
        return BOPBlocks.BLOOD.defaultBlockState().setValue(LiquidBlock.LEVEL, Integer.valueOf(getLegacyLevel(p_76466_)));
    }

    @Override
    public boolean isSame(Fluid p_76456_) {
        // checks if the fluid is the same as this fluid.
        // identity check for both source and flowing variants
        return p_76456_ == BOPFluids.BLOOD || p_76456_ == BOPFluids.FLOWING_BLOOD;
    }

    @Override
    public int getDropOff(LevelReader p_76469_) {
        // how quickly the fluid level decreases as it flows away from the source
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader p_76454_) {
        // how often the fluid updates (ticks) to flow/spread
        return 7;
    }

    @Override
    public boolean canBeReplacedWith(FluidState p_76458_, BlockGetter p_76459_, BlockPos p_76460_, Fluid p_76461_, Direction p_76462_)
    {
        // determines if this fluid can be replaced by another fluid in a given direction
        // this means blood can only be replaced from above by other fluids
        // behaving like water and lava
        return p_76462_ == Direction.DOWN && !p_76461_.is(ModTags.Fluids.BLOOD);
    }

    @Override
    protected float getExplosionResistance() {
        // resistance to explosions (like TNT)
        // it seems blood is very resistant to explosions
        return 100.0F;
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        // sound played when the fluid is picked up with a bucket
        // asset enum
        return Optional.of(SoundEvents.BUCKET_FILL);
    }

    public static class Flowing extends BloodFluid
    {
        // defines the properties of the flowing variant of the blood fluid
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> p_76476_)
        {
            // level property indicates how "full" the fluid block is (0-8)
            super.createFluidStateDefinition(p_76476_);
            p_76476_.add(LEVEL);
        }

        public int getAmount(FluidState p_76480_) {
            //amount of fluid in the block (0-7 for flowing fluids)
            // 8 would be a source block
            //like water and lava.
            return p_76480_.getValue(LEVEL);
        }

        public boolean isSource(FluidState p_76478_) {
            // flowing fluid is never a source block
            // remember that source blocks have 8 amount
            // flowing blocks have 0-7 amount
            // behaves like water and lava
            return false;
        }
    }

    public static class Source extends BloodFluid
    {
        // remember that source is 8 amount
        //and flowing is 0-7 amount
        public int getAmount(FluidState p_76485_) {
            //apparently this can be changed to return different amounts for source blocks?
            return 8;
        }

        public boolean isSource(FluidState p_76483_) {
            // source fluid is always a source block
            return true;
        }
    }
}