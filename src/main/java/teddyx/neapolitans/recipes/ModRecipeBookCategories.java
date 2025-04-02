package teddyx.neapolitans.recipes;

import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import teddyx.neapolitans.Neapolitans;

public class ModRecipeBookCategories {
    public static final RecipeBookCategory MORTAR = register("mortar");
    public static final RecipeBookCategory NEAPOLITAN_PRESS = register("neapolitan_press");

    private static RecipeBookCategory register(String id) {
        return Registry.register(Registries.RECIPE_BOOK_CATEGORY, id, new RecipeBookCategory());
    }

    public static void registerModRecipyBookCategories() {
        Neapolitans.LOGGER.info("Registering mod recipe book categories...");
    }
}
