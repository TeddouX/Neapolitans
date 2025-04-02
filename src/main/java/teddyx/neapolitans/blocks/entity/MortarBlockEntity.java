package teddyx.neapolitans.blocks.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;
import teddyx.neapolitans.items.ModItems;
import teddyx.neapolitans.recipes.MortarRecipe;
import teddyx.neapolitans.recipes.MortarRecipeInput;

import java.util.Optional;

public class MortarBlockEntity extends BlockEntity implements ImplementedInventory {

    private static final int INVENTORY_SIZE = 2;
    private static final int MAX_CRUSHES = 4;
    private static final int MIN_CRUSHES = 1;
    private DefaultedList<ItemStack> items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
    private int crushesCount = 0;

    public MortarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MORTAR_BE, pos, state);
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (this.getWorld() != null)
            this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    public void craft() {
        if (this.getWorld() == null || this.getWorld().isClient())
            return;

        if (this.crushesCount == 0) {
            this.crushesCount = this.getWorld().getRandom().nextBetween(MIN_CRUSHES, MAX_CRUSHES);

            Optional<RecipeEntry<MortarRecipe>> optionalRecipe = ((ServerWorld) this.getWorld()).getRecipeManager()
                    .getFirstMatch(MortarRecipe.Type.INSTANCE, new MortarRecipeInput(this.getStack(0), this.getStack(1)), this.getWorld());

            if (optionalRecipe.isEmpty())
                return;

            ItemStack output = optionalRecipe.get().value().output();

            this.clear();
            this.setStack(0, output);
            this.markDirty();
        }

        this.crushesCount--;
    }

    public boolean onUseWithItem(ItemStack stack) {
        stack.setCount(1);

        if (stack.getItem() == ModItems.PESTLE) {
            this.craft();
            return true;
        }

        if (this.setFirstEmptyStack(stack)) {
            this.markDirty();
            return true;
        }

        return false;
    }

    public void onUseWithEmptyHand() {
        if (this.getWorld() == null || this.isEmpty())
            return;

        for (int i = 0; i < size(); i++) {
            if (getStack(i).isEmpty())
                continue;

            // Spawn the item
            Vec3d vec3d = Vec3d.add(this.getPos(), 0.5, 0.69, 0.5);
            ItemEntity itemEntity = new ItemEntity(this.getWorld(), vec3d.getX(), vec3d.getY(), vec3d.getZ(), this.getStack(i));
            itemEntity.setToDefaultPickupDelay();
            this.getWorld().spawnEntity(itemEntity);

            this.setStack(i, ItemStack.EMPTY);
            this.markDirty();

            return;
        }
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public @NotNull NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup provider) {
        NbtCompound nbt = new NbtCompound();

        this.writeNbt(nbt, provider);

        return nbt;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        Inventories.writeNbt(nbt, this.getItems(), registries);

        nbt.putInt("crushes_count", this.crushesCount);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.items = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.getItems(), registries);

        this.crushesCount = nbt.getInt("crushes_count");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.items;
    }
}
