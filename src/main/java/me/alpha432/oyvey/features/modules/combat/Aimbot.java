package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class Aimbot extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public Aimbot() {
        super("Aimbot", "Automatically aims at players", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.world == null || mc.player == null) return;

        PlayerEntity target = null;
        double closest = 6.0; // Range

        for (PlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            double dist = mc.player.distanceTo(player);

            if (dist < closest) {
                closest = dist;
                target = player;
            }
        }

        if (target != null) {
            aimAt(target);
        }
    }

    private void aimAt(PlayerEntity target) {

        double dx = target.getX() - mc.player.getX();
        double dy = (target.getEyeY()) - mc.player.getEyeY();
        double dz = target.getZ() - mc.player.getZ();

        double dist = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90F);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, dist));

        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
    }
}