package teddyx.neapolitans.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import teddyx.neapolitans.blocks.ModBlocks;
import teddyx.neapolitans.blocks.entity.NeapolitanPressBlockEntity;
import teddyx.neapolitans.network.BlockPosPayload;

public class NeapolitanPressScreenHandler extends ScreenHandler {
    private static final int PLAYER_HOTBAR_Y = 162;
    private static final int PLAYER_HOTBAR_SIZE = 9;
    private static final int PLAYER_INVENTORY_Y = 104;
    private static final int NEAPOLITAN_PRESS_INPUTS_X = 26;
    private static final int NEAPOLITAN_PRESS_INPUTS_Y = 17;
    private static final int NEAPOLITAN_PRESS_OUTPUT_X = 131;
    private static final int NEAPOLITAN_PRESS_OUTPUT_Y = 44;

    private final ScreenHandlerContext context;

    // Client Constructor
    public NeapolitanPressScreenHandler(int synId, PlayerInventory inventory, BlockPosPayload payload) {
        this(synId, inventory, (NeapolitanPressBlockEntity) inventory.player.getWorld().getBlockEntity(payload.pos()));
    }

    // Main constructor
    public NeapolitanPressScreenHandler(int syncId, PlayerInventory inventory, NeapolitanPressBlockEntity blockEntity) {
        super(ModScreenHandlers.NEAPOLITAN_PRESS_SCREEN_HANDLER, syncId);

        this.context = ScreenHandlerContext.create(blockEntity.getWorld(), blockEntity.getPos());

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
        addPressInventory(blockEntity);
    }

    public void addPressInventory(Inventory inventory) {
        // Input slots
        for (int row = 0; row < NeapolitanPressBlockEntity.OUTPUT_SLOT_IDX; row++) {
            addSlot(new Slot(inventory,
                    row,
                    NEAPOLITAN_PRESS_INPUTS_X,
                    NEAPOLITAN_PRESS_INPUTS_Y + (row * 18)));
        }

        // Output slot
        addSlot(new Slot(inventory, NeapolitanPressBlockEntity.OUTPUT_SLOT_IDX, NEAPOLITAN_PRESS_OUTPUT_X, NEAPOLITAN_PRESS_OUTPUT_Y));
    }

    public void addPlayerInventory(PlayerInventory inventory) {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                addSlot(new Slot(inventory,
                        PLAYER_HOTBAR_SIZE + (column + (row * 9)),
                        8 + (column * 18),
                        PLAYER_INVENTORY_Y + (row * 18)));
            }
        }
    }

    public void addPlayerHotbar(PlayerInventory inventory) {
        for (int column = 0; column < 9; column++)
            addSlot(new Slot(inventory, column, 8 + (column * 18), PLAYER_HOTBAR_Y));
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
