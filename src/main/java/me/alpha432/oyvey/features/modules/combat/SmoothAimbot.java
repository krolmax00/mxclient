package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;

public class SmoothAimbot extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public SmoothAimbot() {
        super("SmoothAimbot", "Smooth aiming at players", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {

        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = mc.world.getPlayers().stream()
                .filter(p -> p != mc.player)
                .min(Comparator.comparing(p -> mc.player.distanceTo(p)))
                .orElse(null);

        if (target != null) aim(target);
    }

    private void aim(PlayerEntity target) {

        Vec3d eyes = mc.player.getEyePos();
        Vec3d targetPos = target.getEyePos();

        Vec3d diff = targetPos.subtract(eyes);

        float yaw = (float)Math.toDegrees(Math.atan2(diff.z, diff.x)) - 90F;
        float pitch = (float)-Math.toDegrees(Math.atan2(diff.y, Math.sqrt(diff.x * diff.x + diff.z * diff.z)));

        mc.player.setYaw(mc.player.getYaw() + (yaw - mc.player.getYaw()) / 5f);
        mc.player.setPitch(mc.player.getPitch() + (pitch - mc.player.getPitch()) / 5f);
    }
}