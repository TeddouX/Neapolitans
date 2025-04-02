package teddyx.neapolitans.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import teddyx.neapolitans.items.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                createShaped(RecipeCategory.FOOD, ModItems.NEAPOLITAN_DOUGH, 1)
                        .pattern("sDs")
                        .input('s', Items.SUGAR)
                        .input('D', ModItems.RAW_DOUGH)
                        .criterion(hasItem(ModItems.RAW_DOUGH), conditionsFromItem(ModItems.RAW_DOUGH))
                        .offerTo(recipeExporter);

                createShapeless(RecipeCategory.FOOD, ModItems.CHOCOLATE, 1)
                        .input(Items.MILK_BUCKET, 1)
                        .input(ModItems.COCOA_POWDER, 1)
                        .criterion(hasItem(ModItems.COCOA_POWDER), conditionsFromItem(ModItems.COCOA_POWDER))
                        .offerTo(recipeExporter);

                offerSmelting(List.of(ModItems.RAW_DOUGH), RecipeCategory.FOOD, Items.BREAD, 0.4f, 200, "");
            }
        };
    }

    @Override
    public String getName() {
        return "NeapolitansRecipesProvider";
    }
}
