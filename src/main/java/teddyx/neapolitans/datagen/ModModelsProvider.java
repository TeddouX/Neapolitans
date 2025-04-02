package teddyx.neapolitans.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import teddyx.neapolitans.blocks.ModBlocks;
import teddyx.neapolitans.items.ModItems;

public class ModModelsProvider extends FabricModelProvider {

    public ModModelsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleState(ModBlocks.MORTAR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.COCOA_POWDER, Models.GENERATED);
        itemModelGenerator.register(ModItems.PESTLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLOUR, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.NEAPOLITAN_DOUGH, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHOCOLATE, Models.GENERATED);
    }
}
