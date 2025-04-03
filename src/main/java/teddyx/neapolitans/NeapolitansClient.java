package teddyx.neapolitans;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import teddyx.neapolitans.blocks.entity.ModBlockEntities;
import teddyx.neapolitans.blocks.entity.renderer.MortarBlockEntityRenderer;
import teddyx.neapolitans.blocks.entity.renderer.NeapolitanPressBlockEntityRenderer;
import teddyx.neapolitans.screen.ModScreenHandlers;
import teddyx.neapolitans.screen.NeapolitanPressScreen;

@Environment(EnvType.CLIENT)
public class NeapolitansClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererFactories.register(ModBlockEntities.MORTAR_BE, MortarBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.NEAPOLITAN_PRESS_BE, NeapolitanPressBlockEntityRenderer::new);

        HandledScreens.register(ModScreenHandlers.NEAPOLITAN_PRESS_SCREEN_HANDLER, NeapolitanPressScreen::new);
    }
}
