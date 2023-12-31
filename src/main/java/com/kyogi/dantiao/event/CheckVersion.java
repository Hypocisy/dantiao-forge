package com.kyogi.dantiao.event;

import com.valorin.Main;
import com.valorin.network.Update;
import com.valorin.specialtext.ClickableText;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static com.valorin.configuration.languagefile.MessageSender.sm;

public class CheckVersion implements Listener {
	@EventHandler
	public void check(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!p.isOp()) {
			return;
		}
		if (!Main.getInstance().getConfigManager().isCheckVersion()) {
			return;
		}
		Update update = Main.getInstance().getUpdate();
		new BukkitRunnable() {
			public void run() {
				if (p != null) {
					sendUpdateInfo(update, p);
				}
			}
		}.runTaskLaterAsynchronously(Main.getInstance(), 60);
	}

	public static void sendUpdateInfo(Update update, CommandSender sender) {
		if (update.getState() == 1) {
			String version = update.getVersion();
			String versionNow = Main.getVersion();
			List<String> messageList = update.getContext();
			if (!update.isNew()) {
				sender.sendMessage("");
				sender.sendMessage("§8§l[§bDantiao§8§l]");
				sender.sendMessage("§f- §a单挑插件发现最新版本：§d" + version
						+ "§a可替换现在的旧版本§c" + versionNow);
				sender.sendMessage("§f- §aNew version §d"
						+ version
						+ "§a now is released which can replace the old version §c"
						+ versionNow);
				if (messageList.size() != 0) {
					sender.sendMessage("§7更新内容 | Content:");
					for (String message : messageList) {
						sender.sendMessage("§f- " + message);
					}
				}
				ClickableText.sendWebsiteInfo(sender);
			} else {
				sender.sendMessage("");
				sender.sendMessage("§8§l[§bDantiao§8§l]");
				sender.sendMessage("§f- §7单挑插件已更新到最新版本§a" + versionNow);
				sender.sendMessage("§f- §7(本条消息仅管理员可见)");
				sender.sendMessage("§f- §7Plugin is the lastest version §a"
						+ versionNow);
				sender.sendMessage("§f- §7(This message can only be seen by OPs)");
				sender.sendMessage("");
			}
		}
		if (update.getState() == 2) {
			sm("&7版本更新内容因为超时而获取失败，建议输入/dt checkv手动获取", null);
		}
		if (update.getState() == 3) {
			sm("&7版本更新内容因为某些未知原因（详见后台报错信息）而获取失败，建议输入/dt checkv手动获取", null);
		}
	}
}
