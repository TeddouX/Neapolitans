package teddyx.neapolitans.blocks.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import teddyx.neapolitans.items.ModItems;
import teddyx.neapolitans.network.BlockPosPayload;
import teddyx.neapolitans.recipes.NeapolitanPressRecipe;
import teddyx.neapolitans.recipes.NeapolitanPressRecipeInput;
import teddyx.neapolitans.screen.NeapolitanPressScreenHandler;

import java.util.Optional;

public class NeapolitanPressBlockEntity extends BlockEntity implements GeoBlockEntity, ImplementedInventory, ExtendedScreenHandlerFactory<BlockPosPayload> {
    public static final int INVENTORY_SIZE = 5;

    private static final int PRESS_ANIM_DURATION = (int) 3.5 * 20 + 5; // 3.5 seconds duration (5 ticks margin)
    private static final int CRAFTING_DURATION = PRESS_ANIM_DURATION - 20;

    protected static final RawAnimation PRESS_ANIM = RawAnimation.begin().thenPlay("neapolitan_press.press");
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

    private int animTicks = 0;
    private int craftingTicks = 0;
    private boolean isAnimating = false;
    private boolean isCrafting = false;

    public NeapolitanPressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NEAPOLITAN_PRESS_BE, pos, state);

        setStack(0, new ItemStack(Items.DIRT, 64));
        setStack(1, new ItemStack(ModItems.NEAPOLITAN_DOUGH, 64));
        setStack(2, new ItemStack(Items.COPPER_INGOT, 64));
        setStack(3, new ItemStack(ModItems.NEAPOLITAN_DOUGH, 64));
    }

    public static void tick(World world, BlockPos pos, BlockState state, NeapolitanPressBlockEntity blockEntity) {
        if (world.isClient() || !(world instanceof ServerWorld serverWorld))
            return;

        blockEntity.updateAnimation();
        blockEntity.updateCrafting();

        if (blockEntity.hasRecipe() && !blockEntity.isAnimating && !blockEntity.isCrafting) {
            blockEntity.startCrafting();
        }
    }

    public void startCrafting() {
        this.isAnimating = true;
        this.isCrafting = true;

        this.startPressAnimation();
    }

    public void updateCrafting() {
        if (this.isCrafting && this.craftingTicks >= CRAFTING_DURATION) {
            this.craft();

            this.isCrafting = false;
            this.craftingTicks = 0;
        }

        if (this.isCrafting)
            this.craftingTicks++;
    }

    public void updateAnimation() {
        if (this.isAnimating && this.animTicks >= PRESS_ANIM_DURATION) {
            this.isAnimating = false;
            this.animTicks = 0;
        }

        if (this.isAnimating)
            this.animTicks++;
    }

    public void craft() {
        if (getWorld() == null || getWorld().isClient())
            return;

        Optional<RecipeEntry<NeapolitanPressRecipe>> match = ((ServerWorld) getWorld()).getRecipeManager()
                .getFirstMatch(NeapolitanPressRecipe.Type.INSTANCE,
                        new NeapolitanPressRecipeInput(getStack(0), getStack(1), getStack(2), getStack(3)), getWorld());

        removeStack(0, 1);
        removeStack(1, 1);
        removeStack(2, 1);
        removeStack(3, 1);

        match.ifPresent(recipe ->
                addToSlot(4, recipe.value().result().copy()));
    }

    public boolean hasRecipe() {
        if (getWorld() == null || getWorld().isClient())
            return false;

        Optional<RecipeEntry<NeapolitanPressRecipe>> match = ((ServerWorld) getWorld()).getRecipeManager()
                .getFirstMatch(NeapolitanPressRecipe.Type.INSTANCE,
                        new NeapolitanPressRecipeInput(getStack(0), getStack(1), getStack(2), getStack(3)), getWorld());

        return match.isPresent() && canInsert(match.get().value().result(), 4);
    }

    public void startPressAnimation() {
        triggerAnim("press_controller", "press");
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "press_controller",
                state -> PlayState.STOP)
                .triggerableAnim("press", PRESS_ANIM));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.writeNbt(nbt, this.items, registries);

        nbt.putInt("animation_ticks", this.animTicks);
        nbt.putBoolean("is_animating", this.isAnimating);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);

        Inventories.readNbt(nbt, this.items, registries);

        this.animTicks = nbt.getInt("animation_ticks");
        this.isAnimating = nbt.getBoolean("is_animating");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public BlockPosPayload getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return new BlockPosPayload(this.getPos());
    }

    @Override
    public Text getDisplayName() {
        return Text.literal("Hello");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        this.markDirty();

        return new NeapolitanPressScreenHandler(syncId, playerInventory, this);
    }
}
