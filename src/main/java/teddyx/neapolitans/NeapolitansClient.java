package teddyx.neapolitans;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import teddyx.neapolitans.blocks.entity.ModBlockEntities;
import teddyx.neapolitans.blocks.entity.renderer.MortarBlockEntityRenderer;
import teddyx.neapolitans.blocks.entity.renderer.NeapolitanPressBlockEntityRenderer;

@Environment(EnvType.CLIENT)
public class NeapolitansClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.MORTAR_BE, MortarBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.NEAPOLITAN_PRESS_BE, NeapolitanPressBlockEntityRenderer::new);
    }
}
