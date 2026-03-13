package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class PlayerESP extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public PlayerESP() {
        super("PlayerESP", "Shows players with a green box", Category.RENDER, true, false, false);
    }

    public void onRender3D(MatrixStack matrices, VertexConsumerProvider consumers) {

        if (mc.world == null || mc.player == null) return;

        Vec3d cam = mc.gameRenderer.getCamera().getPos();

        for (PlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            Box box = player.getBoundingBox().offset(
                    -cam.x,
                    -cam.y,
                    -cam.z
            );

            DebugRenderer.drawBox(
                    matrices,
                    consumers,
                    box.minX, box.minY, box.minZ,
                    box.maxX, box.maxY, box.maxZ,
                    0f, 1f, 0f, 1f
            );
        }
    }
}