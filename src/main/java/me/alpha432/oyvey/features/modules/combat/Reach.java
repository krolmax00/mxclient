package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class Reach extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    // Reach Slider: 3.0 - 6.0
    public Setting<Float> reach = register(new Setting<>("Reach", 4.5f, 3.0f, 6.0f));

    public Reach() {
        super("Reach", "Adjustable reach for normal PvP hits (manual)", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        // Nichts automatisch angreifen
        // Du kannst hier Logik für Highlight oder Warnung einbauen, wenn Spieler in Reichweite sind
        if (mc.player == null || mc.world == null) return;

        // Optional: Spieler in Reichweite prüfen
        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player) continue; // dich selbst ignorieren

            double distance = mc.player.distanceTo(entity);
            if (distance <= reach.getValue()) {
                // Hier könnte man z.B. den Spieler markieren / eine Nachricht ausgeben
                // Aber kein automatischer Angriff!
                // System.out.println("Spieler in Reach: " + distance);
            }
        }
    }

    /**
     * Prüft, ob ein Entity innerhalb der Reichweite liegt
     */
    public boolean isInReach(Entity entity) {
        if (mc.player == null || entity == null) return false;
        return mc.player.distanceTo(entity) <= reach.getValue();
    }
}