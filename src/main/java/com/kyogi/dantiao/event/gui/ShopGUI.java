package com.kyogi.dantiao.event.gui;

import com.kyogi.dantiao.DantiaoMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.ContainerEvent;

@Mod.EventBusSubscriber(modid = DantiaoMod.MOD_ID)
public class ShopGUI {
	@EventHandler
	public void page(ContainerEvent event) {
		ServerPlayer player = event;
		String pn = player.getName();
		Inventory inv = event.getInventory();
		if (inv == null) {
			return;
		}
		if (event.getView().getTitle().equals(gm("&0&l积分商城 &9&l[right]", player))) {
			event.setCancelled(true);
			if (event.getRawSlot() == 49) {
				int page = INVShop.pages.get(pn);
				int maxpage = INVShop.getMaxPage();
				if (event.getClick().equals(ClickType.LEFT)) {
					if (page != maxpage) {
						INVShop.loadItem(inv, pn, page + 1);
						pages.put(pn, page + 1);
						INVShop.loadPageItem(inv, pn, page + 1);
					}
				}
				if (event.getClick().equals(ClickType.RIGHT)) {
					if (page != 1) {
						INVShop.loadItem(inv, pn, page - 1);
						pages.put(pn, page - 1);
						INVShop.loadPageItem(inv, pn, page - 1);
					}
				}
			}
			if (event.getRawSlot() > 8 && event.getRawSlot() < 45) // 买东西
			{
				if (inv.getItem(event.getRawSlot()) == null) {
					return;
				}
				int page = INVShop.pages.get(pn);
				int row;
				if ((event.getRawSlot() + 1) % 9 == 0) {
					row = ((event.getRawSlot() + 1) - 9) / 9;
				} else {
					row = ((event.getRawSlot() + 1) - 9) / 9 + 1;
				}
				int column;
				if ((event.getRawSlot() + 1) % 9 == 0) {
					column = 9;
				} else {
					column = (event.getRawSlot() - 9) - (row - 1) * 9 + 1;
				}
				int index = CMDShop.getNum(page, row, column);
				ShopCache shopCache = Main.getInstance().getCacheHandler()
						.getShop();
				PointCache pointCache = Main.getInstance().getCacheHandler()
						.getPoint();
				Good good = shopCache.getList().get(index);
				double price = good.getPrice();
				double points = pointCache.get(pn);
				if (points < price) {
					sm("&c[x]积分余额不足！该商品需要&e{price}&c积分，而你只有&e{points}&c积分", player,
							"price points", new String[] { "" + price,
									"" + points });
					return;
				}
				ItemStack item = good.getItemStack();

				ShopEvent event = new ShopEvent(player, page, row, column, item);
				Bukkit.getServer().getPluginManager().callEvent(event);
				if (event.isCancelled()) { // 购买事件被取消
					return;
				}

				ItemGiver ig = new ItemGiver(player, item);
				if (ig.getIsReceive()) {
					pointCache.set(pn, points - price);
					savepd();
					sml("&7========================================|&a[v]恭喜购买成功，现在你获得了这个道具|&7========================================",
							player);
					if (good.getBroadcast() != null) {
						Bukkit.broadcastMessage(good.getBroadcast()
								.replace("&", "§").replace("_", " ")
								.replace("{player}", player.getName()));
					}
					int num = shopCache.getNumByIndex(index);
					shopCache.updateSalesVolumn(num);
					INVShop.loadInv(pn, inv);
				}
			}
		}
	}
}
