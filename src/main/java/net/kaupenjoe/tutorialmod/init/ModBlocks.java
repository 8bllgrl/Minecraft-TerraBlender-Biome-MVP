package net.kaupenjoe.tutorialmod.init;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.api.block.BOPFluids;
import net.kaupenjoe.tutorialmod.block.*;
import net.kaupenjoe.tutorialmod.block.trees.BOPTreeGrowers;
import net.kaupenjoe.tutorialmod.util.worldgen.BOPSurfaceRuleData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import terrablender.api.SurfaceRuleManager;

import java.util.function.BiConsumer;

import static net.kaupenjoe.tutorialmod.api.block.BOPBlocks.*;

//TODO: check 1.20.1 version
//double check needed ones
public class ModBlocks {
    public static void setup(BiConsumer<ResourceLocation, Block> func) {
        registerBlocks(func);
        registerSurfaceRules();
        //// registerSurfaceRules(); -- Delete or comment out call for MVP clarity // why did gemini say this?
    }

    private static void registerSurfaceRules() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, TutorialMod.MOD_ID, BOPSurfaceRuleData.overworld());
    }

    private static void registerBlocks(BiConsumer<ResourceLocation, Block> func) {
        //redwood
        REDWOOD_SAPLING = register(func, new SaplingBlockBOP(BOPTreeGrowers.REDWOOD, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)), "redwood_sapling");
        REDWOOD_LEAVES = register(func, new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).ignitedByLava().isRedstoneConductor(ModBlocks::never)), "redwood_leaves");
        REDWOOD_LOG = register(func, log(MapColor.CRIMSON_NYLIUM, MapColor.TERRACOTTA_ORANGE, SoundType.WOOD), "redwood_log");
        REDWOOD_WOOD = register(func, new RotatedPillarBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).ignitedByLava().mapColor(MapColor.TERRACOTTA_ORANGE).strength(2.0F).sound(SoundType.WOOD)), "redwood_wood");
        WILLOW_VINE = register(func, new VineBlock(BlockBehaviour.Properties.of().randomTicks().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().strength(0.2F).sound(SoundType.GRASS)), "willow_vine");
        SPANISH_MOSS = register(func, new SpanishMossBottomBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().randomTicks().noCollission().instabreak().sound(SoundType.GRASS)), "spanish_moss");
        SPANISH_MOSS_PLANT = register(func, new SpanishMossBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).replaceable().ignitedByLava().noCollission().instabreak().sound(SoundType.GRASS)), "spanish_moss_plant");
        DEAD_BRANCH = register(func, new DeadBranchBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.COLOR_GRAY).ignitedByLava().noCollission().instabreak().sound(SoundType.WOOD)), "dead_branch");
        DEAD_LOG = register(func, log(MapColor.STONE, MapColor.COLOR_GRAY, SoundType.WOOD), "dead_log");
        DEAD_WOOD = register(func, new RotatedPillarBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).ignitedByLava().mapColor(MapColor.COLOR_GRAY).strength(2.0F).sound(SoundType.WOOD)), "dead_wood");

        //nether
        BLOOD = register(func, new BloodBlock(BOPFluids.FLOWING_BLOOD, BlockBehaviour.Properties.of().replaceable().pushReaction(PushReaction.DESTROY).liquid().noCollission().mapColor(MapColor.CRIMSON_NYLIUM).sound(SoundType.EMPTY).strength(100.0F)), "blood");
        FLESH = register(func, new FleshBlock(BlockBehaviour.Properties.of().randomTicks().mapColor(MapColor.TERRACOTTA_RED).strength(0.4F)), "flesh");
        POROUS_FLESH = register(func, new FleshBlock(BlockBehaviour.Properties.of().randomTicks().mapColor(MapColor.TERRACOTTA_RED).strength(0.4F)), "porous_flesh");
        FLESH_TENDONS = register(func, new FleshTendonsBottomBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_RED).noCollission().strength(0.2F)), "flesh_tendons");
        FLESH_TENDONS_STRAND = register(func, new FleshTendonsBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).mapColor(MapColor.TERRACOTTA_RED).noCollission().strength(0.2F)), "flesh_tendons_strand");
        EYEBULB = register(func, new EyebulbBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_RED).pushReaction(PushReaction.DESTROY).noCollission().strength(0.2F)), "eyebulb");
        HAIR = register(func, new HairBlock(BlockBehaviour.Properties.of().mapColor(MapColor.RAW_IRON).pushReaction(PushReaction.DESTROY).noCollission().instabreak().sound(SoundType.WOOL)), "hair");
        PUS_BUBBLE = register(func, new PusBubbleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_PINK).pushReaction(PushReaction.DESTROY).noCollission().instabreak()), "pus_bubble");
        // TODO: I am unsure if these are visceral heap decor
        // BLACKSTONE_SPINES = register(func, new BlackstoneDecorationBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.DESTROY).noCollission().strength(0.2F)), "blackstone_spines");
        // BLACKSTONE_BULB = register(func, new BlackstoneDecorationBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.DESTROY).noCollission().strength(0.2F).lightLevel((state) -> 2)), "blackstone_bulb");
    }

    private static RotatedPillarBlock log(MapColor MapColor, MapColor MapColor2, SoundType soundType) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).ignitedByLava().mapColor((blockState) -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor : MapColor2).strength(2.0F).sound(soundType));
    }

    private static RotatedPillarBlock logNonIgniting(MapColor MapColor, MapColor MapColor2, SoundType soundType) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.BASS).mapColor((blockState) -> blockState.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor : MapColor2).strength(2.0F).sound(soundType));
    }

    private static Block register(BiConsumer<ResourceLocation, Block> func, Block block, String name) {
        func.accept(new ResourceLocation(TutorialMod.MOD_ID, name), block);
        return block;
    }

    private static Item register(BiConsumer<ResourceLocation, Item> func, String name, Item item) {
        func.accept(new ResourceLocation(TutorialMod.MOD_ID, name), item);
        return item;
    }

    private static Boolean always(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return (boolean) true;
    }

    private static Boolean never(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return (boolean) false;
    }

    private static Boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return (boolean) (p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT);
    }
}
