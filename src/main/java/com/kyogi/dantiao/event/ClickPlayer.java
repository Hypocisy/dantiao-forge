package com.kyogi.dantiao.event;

import com.kyogi.dantiao.DantiaoMod;
import com.kyogi.dantiao.util.SwordChecker;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClickPlayer {
    @SubscribeEvent
    public void showDan(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Entity clickedPlayer = event.getTarget();
        if (!(clickedPlayer instanceof Player)) { // 被点击的若不是玩家则return
            return;
        }
        if (!player.isShiftKeyDown()) { // 点击的玩家应该潜行
            return;
        }
        if (!DantiaoMod.getConfigManager().isClickPlayerToSendRequest()) {
            return;
        }
        if (!SwordChecker.isHoldingSword(player)) {
            return;
        }
        Bukkit.dispatchCommand(player, "dt send " + ((Player) clickedPlayer).getName());
    }
}
