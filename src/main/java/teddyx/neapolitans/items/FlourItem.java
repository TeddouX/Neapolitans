package teddyx.neapolitans.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FlourItem extends Item {
    public FlourItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient())
            return ActionResult.SUCCESS;

        HitResult hitResult = user.raycast(user.getBlockInteractionRange(), 0f, true);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            FluidState fluidState = world.getFluidState(blockPos);

            if (fluidState.isOf(Fluids.WATER)) {
                user.giveOrDropStack(new ItemStack(ModItems.RAW_DOUGH, 1));
                user.getStackInHand(hand).decrementUnlessCreative(1, user);
                world.playSound(null, blockPos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1f, 1f);
            }
        }
        return ActionResult.PASS;
    }
}
