package me.alpha432.oyvey.features.modules.misc;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;

public class PingSpoof extends Module {

    // Ping Slider im ClickGUI
    public Setting<Integer> ping = register(new Setting<>("Ping", 100, 10, 2000));

    private long lastTime = 0;

    public PingSpoof() {
        super("PingSpoof", "Simulates higher ping", Category.MISC, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        long delay = ping.getValue();

        if (System.currentTimeMillis() - lastTime >= delay) {

            // Debug Anzeige
            System.out.println("PingSpoof delay: " + delay + "ms");

            lastTime = System.currentTimeMillis();
        }
    }

    public int getPing() {
        return ping.getValue();
    }
}