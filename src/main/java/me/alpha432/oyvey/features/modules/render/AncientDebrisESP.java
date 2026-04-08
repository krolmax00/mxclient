package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

public class AncientDebrisESP extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AncientDebrisESP() {
        super("AncientDebrisESP", "Finds Ancient Debris", Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {

        if (mc.player == null || mc.world == null) return;

        int radius = 32;
        BlockPos playerPos = mc.player.getBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos pos = playerPos.add(x, y, z);

                    if (mc.world.getBlockState(pos).getBlock() == Blocks.ANCIENT_DEBRIS) {

                        System.out.println("Ancient Debris found at: "
                                + pos.getX() + " "
                                + pos.getY() + " "
                                + pos.getZ());
                    }
                }
            }
        }
    }
}