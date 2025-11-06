package net.kaupenjoe.tutorialmod.worldgen.feature;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.worldgen.feature.configurations.TaigaTreeConfiguration;
import net.kaupenjoe.tutorialmod.worldgen.feature.misc.MossSplatterFeature;
import net.kaupenjoe.tutorialmod.worldgen.feature.tree.BOPTreeFeature;
import net.kaupenjoe.tutorialmod.worldgen.feature.tree.RedwoodTreeFeature;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.BiConsumer;

//TODO: FleshTendonFeature & HangingFleshTendonFeature
public class BOPBaseFeatures {
    public static BOPTreeFeature<TaigaTreeConfiguration> REDWOOD_TREE;

    public static Feature<NoneFeatureConfiguration> MOSS_SPLATTER;
    public static Feature<NoneFeatureConfiguration> FLESH_TENDON;
    public static Feature<NoneFeatureConfiguration> HANGING_FLESH_TENDON;

    public static void registerFeatures(BiConsumer<ResourceLocation, Feature<?>> func) {
        REDWOOD_TREE = register(func, "redwood_tree", new RedwoodTreeFeature(TaigaTreeConfiguration.CODEC));
        MOSS_SPLATTER = register(func, "moss_splatter", new MossSplatterFeature(NoneFeatureConfiguration.CODEC));
        FLESH_TENDON = register(func, "flesh_tendon", new FleshTendonFeature(NoneFeatureConfiguration.CODEC));
        HANGING_FLESH_TENDON = register(func, "hanging_flesh_tendon", new HangingFleshTendonFeature(NoneFeatureConfiguration.CODEC));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> F register(BiConsumer<ResourceLocation, Feature<?>> func, String name, F feature) {
        func.accept(new ResourceLocation(TutorialMod.MOD_ID, name), feature);
        return feature;
    }
}
