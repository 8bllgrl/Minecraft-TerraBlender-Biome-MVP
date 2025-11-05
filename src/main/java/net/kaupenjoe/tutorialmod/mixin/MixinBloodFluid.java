package net.kaupenjoe.tutorialmod.mixin;

import net.kaupenjoe.tutorialmod.block.BloodFluid;
import net.kaupenjoe.tutorialmod.init.ModFluidTypes;
import net.minecraft.world.level.material.FlowingFluid;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BloodFluid.class)
public abstract class MixinBloodFluid extends FlowingFluid
{
    @Override
    public net.minecraftforge.fluids.FluidType getFluidType()
    {
        return ModFluidTypes.BLOOD_TYPE.get(); 
    }
}