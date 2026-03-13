package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class ChestESP extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ChestESP() {
        super("ChestESP", "Highlights chests", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {

        if (mc.world == null || mc.player == null) return;

        int range = 50;

        BlockPos playerPos = mc.player.getBlockPos();

        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {

                    BlockPos pos = playerPos.add(x, y, z);

                    if (mc.world.getBlockState(pos).getBlock() == Blocks.CHEST) {

                        Box box = new Box(pos);

                        drawBox(box);
                    }
                }
            }
        }
    }

    private void drawBox(Box box) {

        // einfache Debug-Ausgabe (damit es compiliert)
        System.out.println("Chest ESP at: " + box.minX + " " + box.minY + " " + box.minZ);
    }
}
