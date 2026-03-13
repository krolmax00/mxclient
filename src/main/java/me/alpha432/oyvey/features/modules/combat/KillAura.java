package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Comparator;

public class KillAura extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public KillAura() {
        super("KillAura", "Automatically attacks players", Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {

        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = mc.world.getPlayers().stream()
                .filter(p -> p != mc.player)
                .filter(p -> mc.player.distanceTo(p) <= 4.5)
                .min(Comparator.comparing(p -> mc.player.distanceTo(p)))
                .orElse(null);

        if (target != null) {

            if (mc.player.getAttackCooldownProgress(0) >= 1) {
                mc.interactionManager.attackEntity(mc.player, target);
                mc.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
            }
        }
    }
}