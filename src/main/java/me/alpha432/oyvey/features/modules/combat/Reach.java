package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

public class Reach extends Module {

    public static double REACH = 4.5;

    public Reach() {
        super("Reach", "Increases attack reach for PvP", Category.COMBAT, true, false, false);
    }

    @Override
    public void onEnable() {
        REACH = 4.5;
    }

    @Override
    public void onDisable() {
        REACH = 3.0;
    }

    public static double getReach() {
        return REACH;
    }
}
