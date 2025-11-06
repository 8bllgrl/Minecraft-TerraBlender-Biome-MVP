package net.kaupenjoe.tutorialmod.biome;

import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.kaupenjoe.tutorialmod.api.sound.BOPSounds;
import net.kaupenjoe.tutorialmod.util.worldgen.placement.BOPNetherPlacements;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BOPNetherBiomes
{
    private static void addFeature(BiomeGenerationSettings.Builder builder, GenerationStep.Decoration step, ResourceKey<PlacedFeature> feature)
    {
        builder.addFeature(step, feature);
    }

    // Visceral Heap Biome
    public static Biome visceralHeap(HolderGetter<PlacedFeature> placedFeatureGetter, HolderGetter<ConfiguredWorldCarver<?>> carverGetter)
    {
        // Mob spawns
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.GHAST, 50, 4, 4));
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.STRIDER, 60, 1, 2));

        // Biome features
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(placedFeatureGetter, carverGetter);
        biomeBuilder.addCarver(GenerationStep.Carving.AIR, Carvers.NETHER_CAVE);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_OPEN);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED);
        BiomeDefaultFeatures.addNetherDefaultOres(biomeBuilder);

        // Custom Features for Visceral Heap
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.BLOOD_LAKE);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.BLOOD_SPRING);
        //TODO: add NETHER_BONE_SPINE
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.NETHER_BONE_SPINE);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.FLESH_TENDON);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.HANGING_FLESH_TENDONS);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.POROUS_FLESH);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.PUS_BUBBLES);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.EYEBULB);
        addFeature(biomeBuilder, GenerationStep.Decoration.UNDERGROUND_DECORATION, BOPNetherPlacements.HAIR);

        // Atmosphere and Sounds
        return new Biome.BiomeBuilder()
            .hasPrecipitation(false).temperature(2.0F).downfall(0.0F)
            .specialEffects((new BiomeSpecialEffects.Builder())
                .waterColor(4159204).waterFogColor(329011) // Murky Water Color
                .fogColor(0x601F18) // Deep Red/Brown Fog
                .ambientLoopSound(SoundEvents.AMBIENT_NETHER_WASTES_LOOP) // sound
                .ambientMoodSound(new AmbientMoodSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_MOOD, 6000, 8, 2.0D)) // mood sound
                .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0111D)) // crimson additions sound
                .backgroundMusic(Musics.createGameMusic(BOPSounds.MUSIC_BIOME_VISCERAL_HEAP)).build()) // music that occurs
            .mobSpawnSettings(spawnBuilder.build()).generationSettings(biomeBuilder.build()).build(); // mob spawn and biome gen
    }
}