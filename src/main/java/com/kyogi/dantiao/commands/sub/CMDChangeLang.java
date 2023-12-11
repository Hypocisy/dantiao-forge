﻿package com.kyogi.dantiao.commands.sub;

import com.valorin.caches.LanguageFileCache;
import com.valorin.commands.SubCommand;
import com.valorin.configuration.languagefile.LanguageFileLoader;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

import static com.valorin.Main.getInstance;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

public class CMDChangeLang extends SubCommand {

    public CMDChangeLang() {
        super("changelang", "changelanguage", "language", "lang", "cl");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label,
                             String[] args) {
        Player p = null;
        if (sender instanceof Player) {
            p = (Player) sender;
        }
        if (args.length == 1) {
            LanguageFileLoader lfl = getInstance().getLanguageFileLoader();
            List<File> flist = lfl.getLanguagesList();
            if (flist.size() == 0) {
                sm("&c[x]服务器不存在任何语言文件，如您有特殊需求请联系腐竹", p);
            } else {
                sm("&6语言文件如下 [right]", p);
                for (File f : flist) {
                    sender.sendMessage("§e" + f.getName().replace(".txt", "")
                            + "§7(" + gm("简体中文", f, 0) + "§7)");
                }
                sm("&6共计 &f&l{amount} &6个", p, "amount", new String[]{""
                        + flist.size()});
            }
            return true;
        }
        if (args.length == 2) {
            if (p == null) {
                sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！", p);
                return true;
            }
            LanguageFileLoader lfl = getInstance().getLanguageFileLoader();
            List<File> flist = lfl.getLanguagesList();
            if (flist.size() != 0) {
                for (File f : flist) {
                    if (args[1].equalsIgnoreCase(f.getName()
                            .replace(".txt", ""))) {
                        LanguageFileCache cache = getInstance()
                                .getCacheHandler().getLanguageFile();
                        cache.set(p.getName(), args[1]);
                        sm("&a[v]语言环境切换已完成", p);
                        return true;
                    }
                }
                sm("&c[x]语言文件&e{name}&c不存在", p, "name", new String[]{""
                        + args[1]});
            } else {
                sm("&c[x]语言文件&e{name}&c不存在", p, "name", new String[]{""
                        + args[1]});
            }
        }
        return true;
    }

}
