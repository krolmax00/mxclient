package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.EntityHitResult;

public class TriggerBot extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private long lastHit = 0;
    private final long COOLDOWN = 250; // zusätzlicher Delay in ms

    public TriggerBot() {
        super("TriggerBot", "Automatically attacks players you look at", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;

        HitResult hit = mc.crosshairTarget;
        if (!(hit instanceof EntityHitResult entityHit)) return;

        Entity entity = entityHit.getEntity();
        if (!(entity instanceof PlayerEntity target)) return;
        if (target == mc.player) return;

        // Reichweite
        if (mc.player.distanceTo(target) > 4.5) return;

        // Minecraft Attack Cooldown prüfen
        if (mc.player.getAttackCooldownProgress(0.5f) < 1.0f) return;

        // zusätzlicher Zeit-Cooldown
        if (System.currentTimeMillis() - lastHit < COOLDOWN) return;

        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        lastHit = System.currentTimeMillis();
    }
}