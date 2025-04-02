package teddyx.neapolitans.blocks.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import teddyx.neapolitans.Neapolitans;
import teddyx.neapolitans.blocks.ModBlocks;

public class ModBlockEntities {

    public static final BlockEntityType<MortarBlockEntity> MORTAR_BE = registerBlockEntity("mortar", MortarBlockEntity::new, ModBlocks.MORTAR);
    public static final BlockEntityType<NeapolitanPressBlockEntity> NEAPOLITAN_PRESS_BE = registerBlockEntity("neapolitan_press", NeapolitanPressBlockEntity::new, ModBlocks.NEAPOLITAN_PRESS);

    static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String name, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Neapolitans.id(name),
                FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }

    public static void registerModBlockEntities() {
        Neapolitans.LOGGER.info("Registering mod block entities");
    }
}
