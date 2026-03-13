package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.util.Hand;

public class ShieldBreaker extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ShieldBreaker() {
        super("ShieldBreaker", "Automatically attacks players holding shields with an axe", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null || mc.player.isUsingItem()) return;

        ClientPlayerEntity player = mc.player;

        // Nur angreifen, wenn Cooldown fertig
        if (player.getAttackCooldownProgress(0.5f) < 1.0f) return;

        // Nur wenn Spieler eine Axt hält
        if (!(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof AxeItem)) return;

        // Gegner suchen (Players in 10 Blöcken, die Schild in der Offhand halten)
        for (LivingEntity entity : mc.world.getEntitiesByClass(LivingEntity.class, player.getBoundingBox().expand(10), e -> e != player)) {
            if (isShieldBlocking(entity)) {
                // Angriff ausführen
                mc.interactionManager.attackEntity(player, entity);
                player.swingHand(Hand.MAIN_HAND);
                break;
            }
        }
    }

    private boolean isShieldBlocking(LivingEntity entity) {
        if (entity instanceof PlayerEntity target) {
            return target.getEquippedStack(EquipmentSlot.OFFHAND).getItem() instanceof ShieldItem;
        }
        return false;
    }
}
