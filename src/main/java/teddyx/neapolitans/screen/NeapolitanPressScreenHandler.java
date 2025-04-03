package teddyx.neapolitans.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import teddyx.neapolitans.blocks.ModBlocks;
import teddyx.neapolitans.blocks.entity.NeapolitanPressBlockEntity;
import teddyx.neapolitans.network.BlockPosPayload;

public class NeapolitanPressScreenHandler extends ScreenHandler {
    private final NeapolitanPressBlockEntity blockEntity;
    private final ScreenHandlerContext context;

    // Client Constructor
    public NeapolitanPressScreenHandler(int synId, PlayerInventory inventory, BlockPosPayload payload) {
        this(synId, inventory, (NeapolitanPressBlockEntity) inventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    // Main constructor
    public NeapolitanPressScreenHandler(int syncId, PlayerInventory inventory, NeapolitanPressBlockEntity blockEntity) {
        super(ModScreenHandlers.NEAPOLITAN_PRESS_SCREEN_HANDLER, syncId);

        this.blockEntity = blockEntity;
        this.context = ScreenHandlerContext.create(blockEntity.getWorld(), blockEntity.getPos());

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    public void addPlayerInventory(PlayerInventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory, 9 + (column + (row * 9)), 8 + (column * 18), 84 + (row * 18)));
            }
        }
    }

    public void addPlayerHotbar(PlayerInventory inventory) {
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(inventory, column, 8 + (column * 18), 142));
        }
    }

    public NeapolitanPressBlockEntity getBlockEntity() {
        return this.blockEntity;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        // TODO: implement this
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.NEAPOLITAN_PRESS);
    }
}
