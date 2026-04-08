package me.alpha432.oyvey.features.modules.render;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import me.alpha432.oyvey.util.render.RenderUtil;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class BlockESP extends Module {

    public Setting<Boolean> diamond = register(new Setting<>("Diamond", true));
    public Setting<Boolean> ancient = register(new Setting<>("AncientDebris", true));
    public Setting<Boolean> spawner = register(new Setting<>("Spawner", true));
    public Setting<Boolean> gilded = register(new Setting<>("GildedBlackstone", true)); // 🔥 NEU

    public Setting<Integer> range = register(new Setting<>("Range", 20, 5, 50));

    private final Set<Block> blocks = new HashSet<>();

    public BlockESP() {
        super("BlockESP", "ESP for selected blocks", Category.RENDER, true, false, false);
    }

    @Subscribe
    public void onRender3D(Render3DEvent event) {

        if (mc.player == null || mc.world == null) return;

        blocks.clear();

        if (diamond.getValue()) blocks.add(Blocks.DIAMOND_ORE);
        if (ancient.getValue()) blocks.add(Blocks.ANCIENT_DEBRIS);
        if (spawner.getValue()) blocks.add(Blocks.SPAWNER);
        if (gilded.getValue()) blocks.add(Blocks.GILDED_BLACKSTONE); // 🔥 NEU

        BlockPos playerPos = mc.player.getBlockPos();
        int r = range.getValue();

        for (BlockPos pos : BlockPos.iterate(
                playerPos.add(-r, -r, -r),
                playerPos.add(r, r, r))) {

            Block block = mc.world.getBlockState(pos).getBlock();

            if (blocks.contains(block)) {

                Color color;

                if (block == Blocks.DIAMOND_ORE) {
                    color = Color.CYAN;
                } else if (block == Blocks.ANCIENT_DEBRIS) {
                    color = Color.RED;
                } else if (block == Blocks.SPAWNER) {
                    color = Color.MAGENTA;
                } else if (block == Blocks.GILDED_BLACKSTONE) {
                    color = Color.YELLOW; // 🔥 GOLD LOOK
                } else {
                    color = Color.WHITE;
                }

                RenderUtil.drawBox(
                        event.getMatrix(),
                        new Box(pos),
                        color,
                        1.5f
                );
            }
        }
    }
}