package net.kaupenjoe.tutorialmod.init;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class ModParticles
{
    // Particle constants for Visceral Heap features only
    public static final SimpleParticleType DRIPPING_BLOOD = new SimpleParticleType(false);
    public static final SimpleParticleType FALLING_BLOOD = new SimpleParticleType(false);
    public static final SimpleParticleType LANDING_BLOOD = new SimpleParticleType(false);
    public static final SimpleParticleType PUS = new SimpleParticleType(false);

    public static void registerParticles(BiConsumer<ResourceLocation, ParticleType<?>> func)
    {
        register(func, "dripping_blood", DRIPPING_BLOOD);
        register(func, "falling_blood", FALLING_BLOOD);
        register(func, "landing_blood", LANDING_BLOOD);
        register(func, "pus", PUS);
    }

    private static <T extends ParticleType<? extends ParticleOptions>> T register(BiConsumer<ResourceLocation, ParticleType<?>> func, String name, T particle)
    {
        func.accept(new ResourceLocation(TutorialMod.MOD_ID, name), particle);
        return particle;
    }
}