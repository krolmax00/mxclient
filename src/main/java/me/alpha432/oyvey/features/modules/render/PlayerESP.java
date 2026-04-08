package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.render.RenderUtil;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.util.math.Box;

import java.awt.*;

public class PlayerESP extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public PlayerESP() {
        super("PlayerESP", "Shows players through walls", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {

        if (mc.world == null || mc.player == null) return;

        for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            Box box = player.getBoundingBox();

            RenderUtil.drawBox(
                    event.getMatrix(),
                    box,
                    Color.RED,
                    2.0
            );
        }
    }
}