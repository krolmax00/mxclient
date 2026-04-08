package me.alpha432.oyvey.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.alpha432.oyvey.event.impl.UpdateEvent;
import me.alpha432.oyvey.features.modules.Module;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class CrystalAura extends Module {

    public CrystalAura() {
        super("CrystalAura", "Basic crystal aura", Category.COMBAT, true, false, false);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {

        if (mc.player == null || mc.world == null) return;

        // 🔥 CRYSTAL BREAK
        for (EndCrystalEntity crystal : mc.world.getEntitiesByClass(EndCrystalEntity.class, mc.player.getBoundingBox().expand(5), e -> true)) {
            mc.interactionManager.attackEntity(mc.player, crystal);
            mc.player.swingHand(Hand.MAIN_HAND);
        }

        // 🔥 TARGET
        PlayerEntity target = mc.world.getPlayers().stream()
                .filter(p -> p != mc.player)
                .filter(p -> mc.player.distanceTo(p) <= 5)
                .findFirst()
                .orElse(null);

        if (target == null) return;

        // 🔥 CHECK HAND
        if (mc.player.getMainHandStack().getItem() != Items.END_CRYSTAL) return;

        // 🔥 PLACE POSITION (unter target)
        BlockPos pos = target.getBlockPos().down();

        // 🔥 PLACE CRYSTAL
        mc.interactionManager.interactBlock(
                mc.player,
                Hand.MAIN_HAND,
                new net.minecraft.util.hit.BlockHitResult(
                        target.getPos(),
                        Direction.UP,
                        pos,
                        false
                )
        );
    }
}