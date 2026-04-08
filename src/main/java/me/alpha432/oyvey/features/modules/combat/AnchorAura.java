package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class AnchorAura extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final double RANGE = 6.0;

    public AnchorAura() {
        super("AnchorAura", "Example block interaction module", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        PlayerEntity target = getTarget();
        if (target == null) return;

        BlockPos pos = target.getBlockPos().down();

        Vec3d hitVec = new Vec3d(
                pos.getX() + 0.5,
                pos.getY() + 1,
                pos.getZ() + 0.5
        );

        BlockHitResult hit = new BlockHitResult(
                hitVec,
                Direction.UP,
                pos,
                false
        );

        // Beispiel Block-Interaktion
        mc.interactionManager.interactBlock(
                mc.player,
                Hand.MAIN_HAND,
                hit
        );

        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private PlayerEntity getTarget() {

        PlayerEntity closest = null;
        double distance = RANGE;

        for (PlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            double d = mc.player.distanceTo(player);

            if (d < distance) {
                distance = d;
                closest = player;
            }
        }

        return closest;
    }

    private int findItem(Item item) {

        for (int i = 0; i < 9; i++) {

            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() == item) {
                return i;
            }
        }

        return -1;
    }
}