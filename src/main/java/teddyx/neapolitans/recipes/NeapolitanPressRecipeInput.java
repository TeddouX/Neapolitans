package teddyx.neapolitans.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record NeapolitanPressRecipeInput(ItemStack sprinkles, ItemStack firstDough, ItemStack cream, ItemStack secondDough) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return switch (slot) {
            case 0 -> sprinkles();
            case 1 -> firstDough();
            case 2 -> cream();
            case 3 -> secondDough();
            default -> throw new IllegalStateException("Unexpected value: " + slot);
        };
    }

    @Override
    public int size() {
        return 4;
    }
}
