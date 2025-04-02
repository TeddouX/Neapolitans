package teddyx.neapolitans.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record MortarRecipeInput(ItemStack first, ItemStack second) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 0 ? first() : second();
    }

    @Override
    public int size() {
        return 2;
    }
}
