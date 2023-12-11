package com.kyogi.dantiao.commands.sub;

import com.valorin.specialtext.ClickableText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.valorin.configuration.languagefile.MessageSender.sm;

public class CMDMainHelp {
    public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
        }
        sm("", p);
        ClickableText.sendHeadInfo(sender);
        sender.sendMessage("§e=============================================");
        sm("", p);
        sm("  &f&l>> &6/dt help(h) &f查看玩家帮助&a[v]", p, false);
        sm("  &f&l>> &6/dt adminhelp(ah) &f查看管理员帮助&a[v]", p, false);
        sm("", p);
        sender.sendMessage("§e=============================================");
        ClickableText.sendWebsiteInfo(sender);
        sm("", p);
        return true;
    }
}
