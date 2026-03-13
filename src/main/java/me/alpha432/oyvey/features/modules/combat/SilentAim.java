package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class SilentAim extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public SilentAim() {
        super("SilentAim", "Attacks players without moving your camera", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = getTarget();

        if (target == null) return;

        if (mc.player.getAttackCooldownProgress(0.5f) < 1f) return;

        if (mc.player.distanceTo(target) > 4.5) return;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }

    private PlayerEntity getTarget() {

        PlayerEntity closest = null;
        double dist = 4.5;

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
