package net.kaupenjoe.tutorialmod.init;

import net.kaupenjoe.tutorialmod.TutorialMod;
import net.kaupenjoe.tutorialmod.api.block.BOPBlocks;
import net.kaupenjoe.tutorialmod.api.block.BOPFluids;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.function.BiConsumer;

import static net.kaupenjoe.tutorialmod.api.item.BOPItems.*;

public class ModItems {
    public static void setup(BiConsumer<ResourceLocation, Item> func) {
        registerItems(func);
        ModVanillaCompat.setup();
    }

    private static void registerItems(BiConsumer<ResourceLocation, Item> func) {
        registerBlockItems(func);

        BOP_ICON = register(func, new Item(new Item.Properties()), "bop_icon");
        
        //nether
        BLOOD_BUCKET = register(func, new BucketItem(BOPFluids.BLOOD, (new Item.Properties()).craftRemainder(Items.BUCKET).stacksTo(1)), "blood_bucket");

    }

    public static void registerBlockItems(BiConsumer<ResourceLocation, Item> func) {
        REDWOOD_SAPLING = register(func, "redwood_sapling", new BlockItem(BOPBlocks.REDWOOD_SAPLING, new Item.Properties()));
        REDWOOD_LEAVES = register(func, "redwood_leaves", new BlockItem(BOPBlocks.REDWOOD_LEAVES, new Item.Properties()));
        REDWOOD_LOG = register(func, "redwood_log", new BlockItem(BOPBlocks.REDWOOD_LOG, new Item.Properties()));
        REDWOOD_WOOD = register(func, "redwood_wood", new BlockItem(BOPBlocks.REDWOOD_WOOD, new Item.Properties()));
        WILLOW_VINE = register(func, "willow_vine", new BlockItem(BOPBlocks.WILLOW_VINE, new Item.Properties()));
        SPANISH_MOSS = register(func, "spanish_moss", new BlockItem(BOPBlocks.SPANISH_MOSS, new Item.Properties()));
        SPANISH_MOSS_PLANT = register(func, "spanish_moss_plant", new BlockItem(BOPBlocks.SPANISH_MOSS_PLANT, new Item.Properties()));
        DEAD_BRANCH = register(func, "dead_branch", new BlockItem(BOPBlocks.DEAD_BRANCH, new Item.Properties()));
        DEAD_LOG = register(func, "dead_log", new BlockItem(BOPBlocks.DEAD_LOG, new Item.Properties()));
        DEAD_WOOD = register(func, "dead_wood", new BlockItem(BOPBlocks.DEAD_WOOD, new Item.Properties()));

 
        //Nether
        BLOOD = register(func, "blood", new BlockItem(BOPBlocks.BLOOD, new Item.Properties()));
        FLESH = register(func, "flesh", new BlockItem(BOPBlocks.FLESH, new Item.Properties()));
        POROUS_FLESH = register(func, "porous_flesh", new BlockItem(BOPBlocks.POROUS_FLESH, new Item.Properties()));
        FLESH_TENDONS = register(func, "flesh_tendons", new BlockItem(BOPBlocks.FLESH_TENDONS, new Item.Properties()));
        FLESH_TENDONS_STRAND = register(func, "flesh_tendons_strand", new BlockItem(BOPBlocks.FLESH_TENDONS_STRAND, new Item.Properties()));
        EYEBULB = register(func, "eyebulb", new BlockItem(BOPBlocks.EYEBULB, new Item.Properties()));
        HAIR = register(func, "hair", new BlockItem(BOPBlocks.HAIR, new Item.Properties()));
        PUS_BUBBLE = register(func, "pus_bubble", new BlockItem(BOPBlocks.PUS_BUBBLE, new Item.Properties()));
        //TODO: unsure if blackstone is needed
        // BLACKSTONE_SPINES = register(func, "blackstone_spines", new BlockItem(BOPBlocks.BLACKSTONE_SPINES, new Item.Properties()));
        // BLACKSTONE_BULB = register(func, "blackstone_bulb", new BlockItem(BOPBlocks.BLACKSTONE_BULB, new Item.Properties()));
        
    }

    private static Item register(BiConsumer<ResourceLocation, Item> func, Item item, String name) {
        return register(func, name, item);
    }

    private static Item register(BiConsumer<ResourceLocation, Item> func, String name, Item item) {
        func.accept(new ResourceLocation(TutorialMod.MOD_ID, name), item);
        return item;
    }
}