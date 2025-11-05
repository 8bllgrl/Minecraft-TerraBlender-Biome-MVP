package net.kaupenjoe.tutorialmod.worldgen;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext; 
import net.minecraft.data.worldgen.placement.PlacementUtils; 
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*; 

import java.util.List;

import net.kaupenjoe.tutorialmod.worldgen.feature.BOPNetherFeatures; 

public class BOPNetherPlacements
{
    public static final ResourceKey<PlacedFeature> BLOOD_LAKE = register("blood_lake");
    public static final ResourceKey<PlacedFeature> BLOOD_SPRING = register("blood_spring");
    public static final ResourceKey<PlacedFeature> FLESH_TENDON = register("flesh_tendon");
    public static final ResourceKey<PlacedFeature> HANGING_FLESH_TENDONS = register("hanging_flesh_tendons");
    public static final ResourceKey<PlacedFeature> POROUS_FLESH = register("porous_flesh");
    public static final ResourceKey<PlacedFeature> NETHER_BONE_SPINE = register("nether_bone_spine");
    public static final ResourceKey<PlacedFeature> PUS_BUBBLES = register("pus_bubbles");
    public static final ResourceKey<PlacedFeature> EYEBULB = register("eyebulb");
    public static final ResourceKey<PlacedFeature> HAIR = register("hair");
    public static final ResourceKey<PlacedFeature> BLACKSTONE_BULBS = register("blackstone_bulbs");
    public static final ResourceKey<PlacedFeature> BLACKSTONE_SPINES = register("blackstone_spines");


    public static void bootstrap(BootstrapContext<PlacedFeature> context)
    {
        // This method manually defines the placement rules (the actual "place" logic).
        // WARNING: If you are running the Data Generator, these definitions might conflict 
        // with the generated JSONs unless those JSONs are disabled or configured correctly.
        
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        final Holder<ConfiguredFeature<?, ?>> BLOOD_LAKE_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.BLOOD_LAKE);
        final Holder<ConfiguredFeature<?, ?>> BLOOD_SPRING_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.BLOOD_SPRING);
        final Holder<ConfiguredFeature<?, ?>> FLESH_TENDON_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.FLESH_TENDON);
        final Holder<ConfiguredFeature<?, ?>> HANGING_FLESH_TENDONS_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.HANGING_FLESH_TENDON);
        final Holder<ConfiguredFeature<?, ?>> POROUS_FLESH_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.POROUS_FLESH);
        final Holder<ConfiguredFeature<?, ?>> NETHER_BONE_SPINE_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.NETHER_BONE_SPINE);
        final Holder<ConfiguredFeature<?, ?>> PUS_BUBBLES_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.PUS_BUBBLES);
        final Holder<ConfiguredFeature<?, ?>> EYEBULB_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.EYEBULB);
        final Holder<ConfiguredFeature<?, ?>> HAIR_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.HAIR);
        final Holder<ConfiguredFeature<?, ?>> BLACKSTONE_BULBS_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.BLACKSTONE_BULBS);
        final Holder<ConfiguredFeature<?, ?>> BLACKSTONE_SPINES_CF = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.BLACKSTONE_SPINES);


        // -----------------------------------------------------------
        // 1. Fluid & Lakes
        // -----------------------------------------------------------
        
        // BLOOD_LAKE: Moderate size lakes, widely spaced (5 per biome attempt)
        register(context, BLOOD_LAKE, BLOOD_LAKE_CF, List.of(
            CountPlacement.of(5), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));
        
        // BLOOD_SPRING: Smaller, more frequent sources, usually higher up (12 per biome attempt)
        register(context, BLOOD_SPRING, BLOOD_SPRING_CF, List.of(
            CountPlacement.of(12), 
            InSquarePlacement.spread(), 
            PlacementUtils.RANGE_4_4, // Range 4, 4: Restricts vertical placement to a central band
            BiomeFilter.biome()
        ));
        
        // -----------------------------------------------------------
        // 2. Structures and Decor
        // -----------------------------------------------------------

        // FLESH_TENDON: Large structure placements
        register(context, FLESH_TENDON, FLESH_TENDON_CF, List.of(
            CountPlacement.of(50), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));

        // HANGING_FLESH_TENDONS: Smaller, more frequent strands hanging from the ceiling/structures
        register(context, HANGING_FLESH_TENDONS, HANGING_FLESH_TENDON_CF, List.of(
            CountPlacement.of(85), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));
        
        // POROUS_FLESH: Ore-like scatter feature for porous flesh blocks
        register(context, POROUS_FLESH, POROUS_FLESH_CF, List.of(
            CountPlacement.of(80), 
            InSquarePlacement.spread(), 
            PlacementUtils.RANGE_10_10, // Targeted placement range 
            BiomeFilter.biome()
        ));
        
        // DECORATIONS (EYEBULB, HAIR, PUS_BUBBLES, BONE)
        
        register(context, PUS_BUBBLES, PUS_BUBBLES_CF, List.of(
            CountPlacement.of(12), 
            InSquarePlacement.spread(), 
            PlacementUtils.RANGE_10_10, 
            BiomeFilter.biome()
        ));
        
        register(context, EYEBULB, EYEBULB_CF, List.of(
            CountPlacement.of(3), 
            InSquarePlacement.spread(), 
            PlacementUtils.RANGE_10_10, 
            BiomeFilter.biome()
        ));
        
        register(context, HAIR, HAIR_CF, List.of(
            CountPlacement.of(22), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));
        
        register(context, NETHER_BONE_SPINE, NETHER_BONE_SPINE_CF, List.of(
            CountPlacement.of(3), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));
        
        register(context, BLACKSTONE_BULBS, BLACKSTONE_BULBS_CF, List.of(
            CountPlacement.of(12), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));
        
        register(context, BLACKSTONE_SPINES, BLACKSTONE_SPINES_CF, List.of(
            CountPlacement.of(100), 
            InSquarePlacement.spread(), 
            PlacementUtils.FULL_RANGE, 
            BiomeFilter.biome()
        ));
        
        // DELETE all non-Visceral Heap placements below this line
    }

    private static ResourceKey<PlacedFeature> register(String name)
    {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(TutorialMod.MOD_ID, name));
    }
    
    // Helper registration methods (copied from PlacementUtils to satisfy dependency)
    protected static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... modifiers)
    {
        register(context, placedFeatureKey, configuredFeature, List.of(modifiers));
    }

    protected static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers)
    {
        context.register(placedFeatureKey, new PlacedFeature(configuredFeature, modifiers));
    }
}