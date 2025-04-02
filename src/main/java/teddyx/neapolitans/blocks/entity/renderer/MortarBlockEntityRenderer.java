package teddyx.neapolitans.blocks.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
import teddyx.neapolitans.blocks.entity.MortarBlockEntity;

@Environment(EnvType.CLIENT)
public class MortarBlockEntityRenderer implements BlockEntityRenderer<MortarBlockEntity> {
    public MortarBlockEntityRenderer(BlockEntityRendererFactory.Context ignoredContext) {}

    public void render(MortarBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Render first item
        matrices.push();
        matrices.translate(0.5, 0.2, 0.5);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(60));
        renderItemStack(blockEntity.getStack(0), light, overlay, matrices, vertexConsumers, blockEntity.getWorld());
        matrices.pop();

        // Render second item
        matrices.push();
        matrices.translate(0.5, 0.2, 0.5);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(60));
        renderItemStack(blockEntity.getStack(1), light, overlay, matrices, vertexConsumers, blockEntity.getWorld());
        matrices.pop();
    }



    private void renderItemStack(ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world) {
        if (!stack.isEmpty())
            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformationMode.GROUND,
                    light, overlay, matrices, vertexConsumers, world, 0);
    }
}
