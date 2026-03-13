package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class TriggerBot extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private long lastHit = 0;

    public TriggerBot() {
        super("TriggerBot", "Automatically attacks entities you look at", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;
        if (mc.crosshairTarget == null) return;

        // kleiner Delay damit es legit wirkt
        if (System.currentTimeMillis() - lastHit < 120) return;

        if (mc.targetedEntity instanceof PlayerEntity target) {

            if (target == mc.player) return;
            if (mc.player.distanceTo(target) > 4.5f) return;

            mc.interactionManager.attackEntity(mc.player, target);
            mc.player.swingHand(Hand.MAIN_HAND);

            lastHit = System.currentTimeMillis();
        }
    }
}