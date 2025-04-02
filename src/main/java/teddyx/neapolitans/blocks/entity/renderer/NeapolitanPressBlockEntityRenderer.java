package teddyx.neapolitans.blocks.entity.renderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import teddyx.neapolitans.Neapolitans;
import teddyx.neapolitans.blocks.entity.NeapolitanPressBlockEntity;

public class NeapolitanPressBlockEntityRenderer extends GeoBlockRenderer<NeapolitanPressBlockEntity> {
    public NeapolitanPressBlockEntityRenderer(BlockEntityRendererFactory.Context ignoredContext) {
        super(new DefaultedBlockGeoModel<>(Neapolitans.id("neapolitan_press")));
    }

    @Override
    public void render(NeapolitanPressBlockEntity blockEntity, float tick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int light, int overlay) {
        super.render(blockEntity, tick, poseStack, bufferSource, light, overlay);


    }
}
