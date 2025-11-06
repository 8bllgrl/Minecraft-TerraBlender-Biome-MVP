package net.kaupenjoe.tutorialmod.util.worldgen.feature;

import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.kaupenjoe.tutorialmod.api.block.BOPFluids;
import net.kaupenjoe.tutorialmod.init.ModTags;
import net.kaupenjoe.tutorialmod.util.worldgen.BOPFeatureUtils;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public class BOPNetherFeatures
{
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLOOD_LAKE = BOPFeatureUtils.createKey("blood_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLOOD_SPRING = BOPFeatureUtils.createKey("blood_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EYEBULB = BOPFeatureUtils.createKey("eyebulb");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLESH_TENDON = BOPFeatureUtils.createKey("flesh_tendon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HANGING_FLESH_TENDON = BOPFeatureUtils.createKey("hanging_flesh_tendon");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POROUS_FLESH = BOPFeatureUtils.createKey("porous_flesh");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUS_BUBBLES = BOPFeatureUtils.createKey("pus_bubbles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HAIR = BOPFeatureUtils.createKey("hair");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context)
    {
        register(context, BOPNetherFeatures.BLOOD_LAKE, Feature.LAKE, new LakeFeature.Configuration(BlockStateProvider.simple(BOPBlocks.BLOOD), BlockStateProvider.simple(BOPBlocks.FLESH)));
        register(context, BOPNetherFeatures.BLOOD_SPRING, Feature.SPRING, new SpringConfiguration(BOPFluids.BLOOD.defaultFluidState(), false, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, Blocks.NETHERRACK, BOPBlocks.FLESH, BOPBlocks.POROUS_FLESH)));
        register(context, BOPNetherFeatures.EYEBULB, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BOPBlocks.EYEBULB))));
        register(context, BOPNetherFeatures.FLESH_TENDON, BOPBaseFeatures.FLESH_TENDON, NoneFeatureConfiguration.INSTANCE);
        register(context, BOPNetherFeatures.HANGING_FLESH_TENDON, BOPBaseFeatures.HANGING_FLESH_TENDON, NoneFeatureConfiguration.INSTANCE);
        register(context, BOPNetherFeatures.POROUS_FLESH, Feature.ORE, new OreConfiguration(new TagMatchTest(ModTags.Blocks.FLESH), BOPBlocks.POROUS_FLESH.defaultBlockState(), 16));
        register(context, BOPNetherFeatures.PUS_BUBBLES, Feature.RANDOM_PATCH, FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(BOPBlocks.PUS_BUBBLE))));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey, F feature, FC configuration)
    {
        context.register(configuredFeatureKey, new ConfiguredFeature<>(feature, configuration));
    }
}