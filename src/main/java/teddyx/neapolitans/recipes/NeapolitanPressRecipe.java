package teddyx.neapolitans.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public record NeapolitanPressRecipe(Ingredient sprinkles, Ingredient firstDough, Ingredient cream, Ingredient secondDough, ItemStack result) implements Recipe<NeapolitanPressRecipeInput> {
    public static final String ID = "neapolitan_press";

    @Override
    public boolean matches(NeapolitanPressRecipeInput input, World world) {
        return sprinkles().test(input.sprinkles())
                && firstDough().test(input.firstDough())
                && cream().test(input.cream())
                && secondDough().test(input.secondDough());
    }

    @Override
    public ItemStack craft(NeapolitanPressRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        if (result().isEmpty()) {
            // Item NBT logic here
        }

        return result().copy();
    }

    @Override
    public RecipeSerializer<NeapolitanPressRecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<NeapolitanPressRecipe> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forShapeless(List.of(sprinkles(), firstDough(), cream(), secondDough()));
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return ModRecipeBookCategories.NEAPOLITAN_PRESS;
    }

    public static class Type implements RecipeType<NeapolitanPressRecipe> {
        public Type () {  }
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<NeapolitanPressRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<NeapolitanPressRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.fieldOf("sprinkles").forGetter(NeapolitanPressRecipe::sprinkles),
                Ingredient.CODEC.fieldOf("first_dough").forGetter(NeapolitanPressRecipe::firstDough),
                Ingredient.CODEC.fieldOf("cream").forGetter(NeapolitanPressRecipe::cream),
                Ingredient.CODEC.fieldOf("second_dough").forGetter(NeapolitanPressRecipe::secondDough),
                ItemStack.CODEC.fieldOf("result").forGetter(NeapolitanPressRecipe::result)
        ).apply(inst, NeapolitanPressRecipe::new));

        public static final PacketCodec<RegistryByteBuf, NeapolitanPressRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.PACKET_CODEC, NeapolitanPressRecipe::sprinkles,
                        Ingredient.PACKET_CODEC, NeapolitanPressRecipe::firstDough,
                        Ingredient.PACKET_CODEC, NeapolitanPressRecipe::cream,
                        Ingredient.PACKET_CODEC, NeapolitanPressRecipe::secondDough,
                        ItemStack.PACKET_CODEC, NeapolitanPressRecipe::result,
                        NeapolitanPressRecipe::new);

        @Override
        public MapCodec<NeapolitanPressRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, NeapolitanPressRecipe> packetCodec() {
            return null;
        }
    }
}
