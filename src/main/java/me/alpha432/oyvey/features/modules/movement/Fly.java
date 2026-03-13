package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import net.minecraft.client.MinecraftClient;

public class Fly extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public Fly() {
        super("Fly", "Creative style flight", Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null) return;

        // Erlaubt fliegen
        mc.player.getAbilities().allowFlying = true;
        mc.player.getAbilities().flying = true;

        // Sync mit Server, sonst merkt er es nicht
        mc.player.sendAbilitiesUpdate();
    }

    @Override
    public void onDisable() {
        if (mc.player == null) return;

        // Flug deaktivieren
        mc.player.getAbilities().flying = false;
        mc.player.getAbilities().allowFlying = false;

        // Syncen
        mc.player.sendAbilitiesUpdate();
    }
}