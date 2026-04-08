package me.alpha432.oyvey.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.PacketEvent;
import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;

public class AnchorMacro198 extends Module {

    private boolean switchedToGlowstone = false;

    public AnchorMacro198() {
        super("AnchorMacro198", "Optimized Anchor Macro like 198 PvP clients", Category.COMBAT, true, false, false);
    }

    @Subscribe
    public void onPacketSend(PacketEvent.Send event) {

        if (mc.player == null || mc.world == null) return;

        // Überprüfen ob wir gerade einen Block platzieren
        if (event.getPacket() instanceof PlayerInteractBlockC2SPacket) {

            // Wenn wir Anchor in der Hand haben und klicken → switch automatisch zu Glowstone
            if (mc.player.getMainHandStack().getItem() == Items.RESPAWN_ANCHOR) {
                switchTo(Items.GLOWSTONE);
                switchedToGlowstone = true;
            }
            // Wenn Glowstone in der Hand & Phase aktiv → switch zurück zu Anchor
            else if (mc.player.getMainHandStack().getItem() == Items.GLOWSTONE && switchedToGlowstone) {
                switchTo(Items.RESPAWN_ANCHOR);
                switchedToGlowstone = false;
            }
        }
    }

    // Hilfsfunktion um Slot zu wechseln
    private void switchTo(net.minecraft.item.Item item) {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() == item) {
                mc.player.getInventory().setSelectedSlot(i); // ✅ richtig für 1.21.5
                return;
            }
        }
    }
}