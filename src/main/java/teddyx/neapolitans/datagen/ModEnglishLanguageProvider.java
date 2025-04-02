package teddyx.neapolitans.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import teddyx.neapolitans.blocks.ModBlocks;
import teddyx.neapolitans.items.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModEnglishLanguageProvider extends FabricLanguageProvider {

    public ModEnglishLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.COCOA_POWDER, "Cocoa Powder");
        translationBuilder.add(ModItems.PESTLE, "Pestle");
        translationBuilder.add(ModBlocks.MORTAR.asItem(), "Mortar");
        translationBuilder.add(ModItems.RAW_DOUGH, "Raw Dough");
        translationBuilder.add(ModItems.NEAPOLITAN_DOUGH, "Neapolitan Dough");
        translationBuilder.add(ModItems.FLOUR, "Flour");
        translationBuilder.add(ModItems.CHOCOLATE, "Chocolate");
        translationBuilder.add(ModBlocks.NEAPOLITAN_PRESS.asItem(), "Mortar");
    }
}
