package teddyx.neapolitans.recipes;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import teddyx.neapolitans.Neapolitans;

public class ModRecipes {

    public static void registerModRecipes() {
        Neapolitans.LOGGER.info("Registering mod recipes...");

        Registry.register(Registries.RECIPE_SERIALIZER, Neapolitans.id(MortarRecipe.ID), MortarRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Neapolitans.id(MortarRecipe.ID), MortarRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER, Neapolitans.id(NeapolitanPressRecipe.ID), NeapolitanPressRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Neapolitans.id(NeapolitanPressRecipe.ID), NeapolitanPressRecipe.Type.INSTANCE);
    }
}
