package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public class MaceBoost extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public MaceBoost() {
        super("MaceBoost", "Improves Mace attack timing", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;

        // Nur aktiv wenn Mace gehalten wird
        if (mc.player.getMainHandStack().getItem() != Items.MACE) return;

        PlayerEntity player = mc.player;

        // Wenn fallend → bessere Hit-Gelegenheit
        if (!player.isOnGround() && player.fallDistance > 1.5f) {

            // leichte Vorwärtsbewegung für besseren Hit
            player.setVelocity(
                    player.getVelocity().x * 1.1,
                    player.getVelocity().y,
                    player.getVelocity().z * 1.1
            );
        }
    }
}