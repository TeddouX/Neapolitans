package teddyx.neapolitans.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import teddyx.neapolitans.Neapolitans;

import java.util.function.Function;

public class ModBlocks {

    public static final Block MORTAR = register("mortar", MortarBlock::new,
            AbstractBlock.Settings.create().nonOpaque().blockVision(Blocks::never), true);
    public static final Block NEAPOLITAN_PRESS = register("neapolitan_press", NeapolitanPressBlock::new,
            AbstractBlock.Settings.create().nonOpaque().blockVision(Blocks::never), true);

    private static Block register(String name, AbstractBlock.Settings settings, boolean registerItem) {
        return register(name, Block::new, settings, registerItem);
    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, boolean registerItem) {
        Identifier id = Neapolitans.id(name);
        RegistryKey<Block> blockResourceKey = RegistryKey.of(RegistryKeys.BLOCK, id);
        Block block = factory.apply(settings.registryKey(blockResourceKey));

        if (registerItem) {
            RegistryKey<Item> itemResourceKey = RegistryKey.of(RegistryKeys.ITEM, id);
            Item blockItem = new BlockItem(block, new Item.Settings().maxCount(64).registryKey(itemResourceKey));

            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void registerModBlocks() {
        Neapolitans.LOGGER.info("Registering blocks...");
    }

}
