package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class AncientDebrisESP extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AncientDebrisESP() {
        super("AncientDebrisESP", "Highlights Ancient Debris", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {

        if (mc.world == null || mc.player == null) return;

        MatrixStack matrices = new MatrixStack();
        VertexConsumerProvider consumers = mc.getBufferBuilders().getEntityVertexConsumers();

        BlockPos playerPos = mc.player.getBlockPos();

        int radius = 16;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos pos = playerPos.add(x, y, z);

                    if (mc.world.getBlockState(pos).getBlock() == Blocks.ANCIENT_DEBRIS) {

                        Box box = new Box(pos);

                        DebugRenderer.drawBox(
                                matrices,
                                consumers,
                                box,
                                1f, 0f, 0f, 1f
                        );
                    }
                }
            }
        }
    }
}