package me.alpha432.oyvey.features.modules.render;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.util.math.Vec3d;

public class Tracers extends Module {

    public Tracers() {
        super("Tracers", "Working tracers", Category.RENDER, true, false, false);
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {

        if (mc.player == null || mc.world == null) return;

        Vec3d cam = mc.gameRenderer.getCamera().getPos();

        VertexConsumerProvider.Immediate provider =
                mc.getBufferBuilders().getEntityVertexConsumers();

        VertexConsumer buffer = provider.getBuffer(RenderLayer.getLines());

        for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            Vec3d target = player.getPos().add(0, player.getHeight() / 2.0, 0);

            float x = (float)(target.x - cam.x);
            float y = (float)(target.y - cam.y);
            float z = (float)(target.z - cam.z);

            // Linie von Kamera -> Spieler
            buffer.vertex(event.getMatrix().peek().getPositionMatrix(), 0, 0, 0)
                    .color(0, 255, 0, 255);

            buffer.vertex(event.getMatrix().peek().getPositionMatrix(), x, y, z)
                    .color(0, 255, 0, 255);
        }

        provider.draw(); // 🔥 WICHTIG!
    }
}