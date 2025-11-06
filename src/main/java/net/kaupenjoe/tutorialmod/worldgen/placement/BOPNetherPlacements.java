package net.kaupenjoe.tutorialmod.worldgen.placement;

import net.kaupenjoe.tutorialmod.worldgen.BOPPlacementUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class BOPNetherPlacements
{
    public static final ResourceKey<PlacedFeature> EYEBULB = BOPPlacementUtils.createKey("eyebulb");
    public static final ResourceKey<PlacedFeature> FLESH_TENDON = BOPPlacementUtils.createKey("flesh_tendon");
    public static final ResourceKey<PlacedFeature> HANGING_FLESH_TENDONS = BOPPlacementUtils.createKey("hanging_flesh_tendons");
    public static final ResourceKey<PlacedFeature> POROUS_FLESH = BOPPlacementUtils.createKey("porous_flesh");
    public static final ResourceKey<PlacedFeature> PUS_BUBBLES = BOPPlacementUtils.createKey("pus_bubbles");

    public static void bootstrap(BootstapContext<PlacedFeature> context)
    {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        //TODO: BOPNetherFeatures https://github.com/Glitchfiend/BiomesOPlenty/blob/1.20.1/common/src/main/java/biomesoplenty/worldgen/feature/BOPNetherFeatures.java
        final Holder<ConfiguredFeature<?, ?>> EYEBULB = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.EYEBULB);
        final Holder<ConfiguredFeature<?, ?>> FLESH_TENDON = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.FLESH_TENDON);
        final Holder<ConfiguredFeature<?, ?>> HANGING_FLESH_TENDON = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.HANGING_FLESH_TENDON);
        final Holder<ConfiguredFeature<?, ?>> POROUS_FLESH = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.POROUS_FLESH);
        final Holder<ConfiguredFeature<?, ?>> PUS_BUBBLES = configuredFeatureGetter.getOrThrow(BOPNetherFeatures.PUS_BUBBLES);

        register(context, BOPNetherPlacements.EYEBULB, EYEBULB, List.of(CountPlacement.of(3), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome()));
        register(context, BOPNetherPlacements.FLESH_TENDON, FLESH_TENDON, List.of(CountPlacement.of(50), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, BOPNetherPlacements.HANGING_FLESH_TENDONS, HANGING_FLESH_TENDON, List.of(CountPlacement.of(85), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome()));
        register(context, BOPNetherPlacements.POROUS_FLESH, POROUS_FLESH, List.of(CountPlacement.of(80), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome()));
        register(context, BOPNetherPlacements.PUS_BUBBLES, PUS_BUBBLES, List.of(CountPlacement.of(12), InSquarePlacement.spread(), PlacementUtils.RANGE_10_10, BiomeFilter.biome()));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, PlacementModifier... modifiers)
    {
        register(context, placedFeatureKey, configuredFeature, List.of(modifiers));
    }

    protected static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> placedFeatureKey, Holder<ConfiguredFeature<?, ?>> configuredFeature, List<PlacementModifier> modifiers)
    {
        context.register(placedFeatureKey, new PlacedFeature(configuredFeature, modifiers));
    }
}