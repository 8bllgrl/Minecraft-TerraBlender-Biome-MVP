package net.kaupenjoe.tutorialmod.init;

import biomesoplenty.core.BiomesOPlenty;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.BiConsumer;

import static biomesoplenty.api.sound.BOPSounds.*;

public class ModSounds
{
    public static void registerSounds(BiConsumer<ResourceLocation, SoundEvent> func)
    {
        MUSIC_BIOME_VISCERAL_HEAP = registerForHolder(func, "music.nether.visceral_heap");
        BLOOD_AMBIENT = register(func, "block.blood.ambient");
        FLESH_TENDON_DRIP = register(func, "block.flesh_tendon.drip");
        PUS_BUBBLE_POP = register(func, "block.pus_bubble.pop");
    }

    private static Holder<SoundEvent> registerForHolder(BiConsumer<ResourceLocation, SoundEvent> func, String name)
    {
        ResourceLocation location = new ResourceLocation(BiomesOPlenty.MOD_ID, name);
        ResourceKey<SoundEvent> key = ResourceKey.create(Registries.SOUND_EVENT, location);

        SoundEvent event = SoundEvent.createVariableRangeEvent(location);
        func.accept(location, event);
        return BuiltInRegistries.SOUND_EVENT.getHolder(key).orElseThrow();
    }

    private static SoundEvent register(BiConsumer<ResourceLocation, SoundEvent> func, String name)
    {
        ResourceLocation location = new ResourceLocation(BiomesOPlenty.MOD_ID, name);
        SoundEvent event = SoundEvent.createVariableRangeEvent(location);
        func.accept(location, event);
        return event;
    }
}