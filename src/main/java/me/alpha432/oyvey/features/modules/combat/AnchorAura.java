package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class AnchorAura extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AnchorAura() {
        super("AnchorAura", "Automatically places and explodes anchors", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = getTarget();

        if (target == null) return;

        BlockPos pos = target.getBlockPos().down();

        // Place anchor
        if (mc.world.getBlockState(pos).isAir()) {

            if (mc.player.getMainHandStack().getItem() == Items.RESPAWN_ANCHOR) {

                mc.interactionManager.interactBlock(
                        mc.player,
                        Hand.MAIN_HAND,
                        new net.minecraft.util.hit.BlockHitResult(
                                pos.toCenterPos(),
                                net.minecraft.util.math.Direction.UP,
                                pos,
                                false
                        )
                );

                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }

        // Charge anchor
        if (mc.world.getBlockState(pos).getBlock() == Blocks.RESPAWN_ANCHOR) {

            if (mc.player.getMainHandStack().getItem() == Items.GLOWSTONE) {

                mc.interactionManager.interactBlock(
                        mc.player,
                        Hand.MAIN_HAND,
                        new net.minecraft.util.hit.BlockHitResult(
                                pos.toCenterPos(),
                                net.minecraft.util.math.Direction.UP,
                                pos,
                                false
                        )
                );

                mc.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

    private PlayerEntity getTarget() {

        PlayerEntity closest = null;
        double dist = 6;

        for (PlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            double d = player.distanceTo(mc.player);

            if (d < dist) {
                dist = d;
                closest = player;
            }
        }

        return closest;
    }
}
