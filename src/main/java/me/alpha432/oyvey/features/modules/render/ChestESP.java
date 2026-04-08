package me.alpha432.oyvey.features.modules.render;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.render.RenderUtil;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.awt.*;

public class ChestESP extends Module {

    public ChestESP() {
        super("ChestESP", "Shows nearby chests", Category.RENDER, true, false, false);
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {

        if (mc.player == null || mc.world == null) return;

        BlockPos playerPos = mc.player.getBlockPos();

        int radius = 20; // 🔥 kleiner radius = kein lag

        for (BlockPos pos : BlockPos.iterate(
                playerPos.add(-radius, -10, -radius),
                playerPos.add(radius, 10, radius))) {

            BlockEntity be = mc.world.getBlockEntity(pos);

            if (be instanceof ChestBlockEntity) {

                RenderUtil.drawBox(
                        event.getMatrix(),
                        new Box(pos),
                        Color.GREEN,
                        1.5f
                );
            }
        }
    }
}