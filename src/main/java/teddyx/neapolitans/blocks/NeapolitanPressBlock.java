package teddyx.neapolitans.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import teddyx.neapolitans.blocks.entity.ModBlockEntities;
import teddyx.neapolitans.blocks.entity.NeapolitanPressBlockEntity;

public class NeapolitanPressBlock extends BlockWithEntity {
    public static final MapCodec<NeapolitanPressBlock> CODEC = createCodec(NeapolitanPressBlock::new);

    protected NeapolitanPressBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected MapCodec<NeapolitanPressBlock> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null : BlockWithEntity.validateTicker(type, ModBlockEntities.NEAPOLITAN_PRESS_BE, NeapolitanPressBlockEntity::tick);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new NeapolitanPressBlockEntity(pos, state);
    }
}
