package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class FastXP extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public Setting<Integer> delay = register(new Setting<>("Delay", 1, 0, 5));

    private int tickCounter = 0;

    public FastXP() {
        super("FastXP", "Throws XP bottles faster", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (mc.player == null || mc.world == null) return;

        tickCounter++;
        if (tickCounter < delay.getValue()) return;
        tickCounter = 0;

        // Prüfen ob XP in der Hand
        if (mc.player.getMainHandStack().getItem() == Items.EXPERIENCE_BOTTLE) {
            // Public API nutzen
            mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
    }
}