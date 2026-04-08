package me.alpha432.oyvey.features.modules.combat;

import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ShieldBreaker extends Module {

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ShieldBreaker() {
        super("ShieldBreaker", "Automatically swaps to axe when enemy blocks", Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {

        if (mc.player == null || mc.world == null) return;

        PlayerEntity target = null;

        for (PlayerEntity player : mc.world.getPlayers()) {

            if (player == mc.player) continue;

            if (player.isBlocking()) {
                target = player;
                break;
            }
        }

        if (target == null) return;

        int axeSlot = findAxe();

        if (axeSlot == -1) return;

        int oldSlot = mc.player.getInventory().getSelectedSlot();

        // swap to axe
        mc.player.getInventory().setSelectedSlot(axeSlot);

        // attack
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);

        // swap back
        mc.player.getInventory().setSelectedSlot(oldSlot);
    }

    private int findAxe() {

        for (int i = 0; i < 9; i++) {

            ItemStack stack = mc.player.getInventory().getStack(i);

            if (stack.getItem() instanceof AxeItem) {
                return i;
            }
        }

        return -1;
    }
}