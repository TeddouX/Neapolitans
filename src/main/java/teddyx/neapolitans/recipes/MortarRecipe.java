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
import java.util.Optional;

public record MortarRecipe(Optional<Ingredient> first, Optional<Ingredient> second, ItemStack output) implements Recipe<MortarRecipeInput> {
    public static final String ID = "mortar";

    @Override
    public boolean matches(MortarRecipeInput input, World world) {
        // ?
        return (Ingredient.matches(this.first(), input.first())
            && Ingredient.matches(this.second(), input.second()))
            || (Ingredient.matches(this.first(), input.second())
            && Ingredient.matches(this.second(), input.first()));
    }

    @Override
    public ItemStack craft(MortarRecipeInput input, RegistryWrapper.WrapperLookup registries) {
        return this.output().copy();
    }

    @Override
    public RecipeSerializer<MortarRecipe> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<MortarRecipe> getType() {
        return Type.INSTANCE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forMultipleSlots(List.of(this.first(), this.second()));
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        return ModRecipeBookCategories.MORTAR;
    }

    public static class Type implements RecipeType<MortarRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<MortarRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        public static final MapCodec<MortarRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC.optionalFieldOf("first").forGetter(MortarRecipe::first),
                Ingredient.CODEC.optionalFieldOf("second").forGetter(MortarRecipe::second),
                ItemStack.CODEC.fieldOf("result").forGetter(MortarRecipe::output)
        ).apply(inst, MortarRecipe::new));

        public static final PacketCodec<RegistryByteBuf, MortarRecipe> STREAM_CODEC =
                PacketCodec.tuple(
                        Ingredient.OPTIONAL_PACKET_CODEC, MortarRecipe::first,
                        Ingredient.OPTIONAL_PACKET_CODEC, MortarRecipe::second,
                        ItemStack.PACKET_CODEC, MortarRecipe::output,
                        MortarRecipe::new);

        @Override
        public MapCodec<MortarRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, MortarRecipe> packetCodec() {
            return STREAM_CODEC;
        }
    }
}
