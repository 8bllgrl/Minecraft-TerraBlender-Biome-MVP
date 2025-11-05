package net.kaupenjoe.tutorialmod.init;

import net.kaupenjoe.tutorialmod.BiomesOPlentyForge;
import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModFluidTypes
{
    public static RegistryObject<FluidType> BLOOD_TYPE;
    // LIQUID_NULL_TYPE is removed

    public static void setup()
    {
        registerFluids();
    }

    public static void registerFluids()
    {
        // 1. BLOOD_TYPE REGISTRATION
        BLOOD_TYPE = registerFluidType(() -> new FluidType(FluidType.Properties.create()
                        .descriptionId("block.biomesoplenty.blood")
                        .fallDistanceModifier(0F)
                        .canExtinguish(true)
                        .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                        .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                        .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                        .density(3000) // High density/viscosity helps define the heavy look
                        .viscosity(6000))
        {
            @Override
            public @Nullable PathType getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog)
            {
                return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
            }

            @Override
            public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer)
            {
                // Client-side properties: textures and fog
                consumer.accept(new IClientFluidTypeExtensions()
                {
                    private static final ResourceLocation BLOOD_UNDERWATER = new ResourceLocation("biomesoplenty:textures/block/blood_underwater.png"),
                            BLOOD_STILL = new ResourceLocation("biomesoplenty:block/blood_still"),
                            BLOOD_FLOW = new ResourceLocation("biomesoplenty:block/blood_flow");

                    @Override
                    public ResourceLocation getStillTexture() { return BLOOD_STILL; }
                    @Override
                    public ResourceLocation getFlowingTexture() { return BLOOD_FLOW; }
                    @Override
                    public ResourceLocation getRenderOverlayTexture(Minecraft mc) { return BLOOD_UNDERWATER; }

                    @Override
                    public Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor)
                    {
                        return new Vector3f(0.407F, 0.121F, 0.137F); // Deep red/brown
                    }
                });
            }
        }, "blood");
        
        // LIQUID_NULL_TYPE registration is removed
    }

    public static RegistryObject<FluidType> registerFluidType(Supplier<FluidType> fluidSupplier, String name)
    {
        return BiomesOPlentyForge.FORGE_FLUID_REGISTER.register(name, fluidSupplier);
    }

    public static void registerFluidInteractions()
    {
        // 2. FLUID INTERACTION LOGIC
        for (Map.Entry<ResourceKey<FluidType>, FluidType> fluidType : ForgeRegistries.FLUID_TYPES.get().getEntries())
        {
            // If the fluid type is not the Empty (air) type and not our blood type
            if (fluidType.getValue() != ForgeMod.EMPTY_TYPE.get() && fluidType.getValue() != ModFluidTypes.BLOOD_TYPE.get())
            {
                FluidInteractionRegistry.addInteraction(fluidType.getValue(), new FluidInteractionRegistry.InteractionInformation(
                        ModFluidTypes.BLOOD_TYPE.get(),
                        // Interaction: If liquid is a source block, make solid FLESH. Otherwise, make porous FLESH.
                        fluidState -> fluidState.isSource() ? BOPBlocks.FLESH.defaultBlockState() : BOPBlocks.POROUS_FLESH.defaultBlockState()
                ));
            }
            // LIQUID_NULL_TYPE interaction is removed
        }
    }
}