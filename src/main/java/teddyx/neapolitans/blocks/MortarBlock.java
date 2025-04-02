package teddyx.neapolitans.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import teddyx.neapolitans.blocks.entity.MortarBlockEntity;

public class MortarBlock extends BlockWithEntity {

    private static final MapCodec<MortarBlock> CODEC = createCodec(MortarBlock::new);
    private final VoxelShape SHAPE = VoxelShapes.union(
            Block.createCuboidShape(5f, 0f, 5f, 11f, 2f, 11f),      // Bottom
            Block.createCuboidShape(5f, 2f, 3f, 11f, 11f, 5f),     // North
            Block.createCuboidShape(11f, 2f, 5f, 13f, 11f, 11f),  // East
            Block.createCuboidShape(5f, 2f, 11f, 11f, 11f, 13f), // South
            Block.createCuboidShape(3f, 2f, 5f, 5f, 11f, 11f)   // West
    );

    public MortarBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPE;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient())
            return ActionResult.SUCCESS;

        if (!(world.getBlockEntity(pos) instanceof MortarBlockEntity blockEntity))
            return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;

        ItemStack playerStack = player.getStackInHand(hand);
        if (!playerStack.isEmpty()) {
            if (blockEntity.onUseWithItem(playerStack.copy()))
                // The block entity's inventory isn't full
                playerStack.decrementUnlessCreative(1, player);
        }
        else
            blockEntity.onUseWithEmptyHand();

        return ActionResult.SUCCESS;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MortarBlockEntity(pos, state);
    }
}
