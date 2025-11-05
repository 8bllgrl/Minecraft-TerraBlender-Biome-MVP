package net.kaupenjoe.tutorialmod.worldgen;

import net.kaupenjoe.tutorialmod.api.biome.BOPBiomes;
import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.util.biome.BiomeUtil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;
import java.util.function.Consumer;

public class BOPNetherRegionCommon extends Region
{
    public static final ResourceLocation LOCATION = new ResourceLocation(TutorialMod.MOD_ID, "nether_common");
    
    // Weight should remain low to allow vanilla biomes to spawn if any exist later
    public BOPNetherRegionCommon(int weight)
    {
        super(LOCATION, RegionType.NETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        // Add Visceral Heap across the entire climate space (FULL_RANGE)
        
        // this also controls the biome's rarity, since it's the only biome in this region here, it will spawn frequently, but allows for vanilla biomes to spawn too
        this.addBiome(mapper, FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, FULL_RANGE, 0.0F, 
                      BiomeUtil.biomeOrFallback(registry, BOPBiomes.VISCERAL_HEAP, Biomes.NETHER_WASTES));
    }
}