package com.kyogi.dantiao.event;

import com.kyogi.dantiao.event.game.*;
import com.kyogi.dantiao.event.gui.RecordsGUI;
import com.kyogi.dantiao.event.gui.ShopGUI;
import com.kyogi.dantiao.event.gui.StartGUI;
import com.kyogi.dantiao.event.requests.LeaveServer;
import com.kyogi.dantiao.event.sign.ClickSign;
import com.kyogi.dantiao.event.sign.CreateSign;

public class EventRegister {
	public static void registerEvents() {
		EventRegister[] listeners = { new EndGame(), new Protection(), new PVP(),
				new Teleport(), new ArenaCreate(), new Chat(),
				new RecordsGUI(), new ShopGUI(), new StartGUI(),
				new TypeCommands(), new Move(), new JoinServer(),
				new LeaveServer(), new ClickSign(), new CreateSign(),
				new CheckVersion(), new CommandTypeAmountStatistics(),
				new CompulsoryTeleport(), new ClickPlayer() };
		for (Listener listener : listeners) {
			Bukkit.getPluginManager().registerEvents(listener,
					Main.getInstance());
		}
	}
}
