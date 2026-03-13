package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public AutoTotem() {
        super("AutoTotem", "Automatically equips a Totem", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;

        equipTotem();
    }

    private void equipTotem() {

        if (mc.player.getOffHandStack().getItem() == Items.TOTEM_OF_UNDYING) return;

        int slot = findTotem();

        if (slot == -1) return;

        int syncId = mc.player.currentScreenHandler.syncId;

        // Totem aufnehmen
        mc.interactionManager.clickSlot(
                syncId,
                slot,
                0,
                SlotActionType.PICKUP,
                mc.player
        );

        // In Offhand legen
        mc.interactionManager.clickSlot(
                syncId,
                45,
                0,
                SlotActionType.PICKUP,
                mc.player
        );

        // Cursor zurück
        mc.interactionManager.clickSlot(
                syncId,
                slot,
                0,
                SlotActionType.PICKUP,
                mc.player
        );
    }

    private int findTotem() {

        for (int i = 0; i < mc.player.getInventory().size(); i++) {

            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() == Items.TOTEM_OF_UNDYING) {
                return i;
            }
        }

        return -1;
    }
}
