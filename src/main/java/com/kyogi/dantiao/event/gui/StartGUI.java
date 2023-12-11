package com.kyogi.dantiao.event.gui;

import com.kyogi.dantiao.DantiaoMod;

public class StartGUI implements Listener {
	@EventHandler
	public void page(InventoryClickEvent e) {
		Player opener = (Player) e.getWhoClicked();
		String opener_name = opener.getName();
		Inventory inventory = e.getInventory();
		if (inventory == null) {
			return;
		}
		if (inventory.getHolder() == null) {
			return;
		}
		if (!(inventory.getHolder() instanceof StartInvHolder)) {
			return;
		}
		StartInvHolder holder = (StartInvHolder) inventory.getHolder();
		e.setCancelled(true);
		if (e.getRawSlot() != 13) {
			return;
		}
		if (holder.getStatus() == 0) {
			SearchingQueue queue = DantiaoMod.getInstance().getSearchingQueue();
			String waiter_name = queue.getWaiter();
			if (waiter_name == null) {
				queue.setWaiter(opener_name);
				INVStart.startSearch(holder);
			} else {
				queue.setWaiter(null);
				Player waiter = Bukkit.getPlayerExact(waiter_name);
				new StartGame(opener, waiter, null, null, 1);
			}
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Inventory inventory = e.getInventory();
		if (inventory == null) {
			return;
		}
		if (inventory.getHolder() == null) {
			return;
		}
		if (!(inventory.getHolder() instanceof StartInvHolder)) {
			return;
		}
		StartInvHolder holder = (StartInvHolder) inventory.getHolder();
		SearchingQueue queue = Main.getInstance().getSearchingQueue();
		if (holder.getStatus() == 1) {
			queue.setWaiter(null);
			INVStart.finishSearch(holder, true);
		}
	}
}
