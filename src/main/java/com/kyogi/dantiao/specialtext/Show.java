package com.kyogi.dantiao.specialtext;

import net.minecraft.network.chat.Component;

import java.util.List;

public class Show {
	public static void showRecord(Player player,ItemStack recordItem,Player shower) {
		
		Component txt = Component.empty();
		if (shower != null) {
			txt(gm("&f{shower} &7: &b[分享：{player}的单挑战绩&7(鼠标移动到此处查看详情)&b]",null
					,"shower player",new String[]{shower.getName(), player.getName()}));
		} else {
			txt.setText(gm("&f{shower} &7: &b[分享：{player}的单挑战绩&7(鼠标移动到此处查看详情)&b]",null
					,"shower player",new String[]{player.getName(), player.getName()}));
		}
	    List<String> lore = recordItem.getItemMeta().getLore();
	    lore.remove(lore.size() - 1);lore.remove(lore.size() - 1);lore.remove(lore.size() - 1);
	    String n = gm("&2{player}：",player,"player",new String[]{player.getName()})+
	    		recordItem.getItemMeta().getDisplayName();
	    for (String s : lore) {
	      n = n+"\n"+s;
	    }
	    n.substring(2);
	    txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(n).create()));
	    Bukkit.spigot().broadcast(txt);
	}
}
