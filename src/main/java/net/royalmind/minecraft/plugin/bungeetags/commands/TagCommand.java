package net.royalmind.minecraft.plugin.bungeetags.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.royalmind.minecraft.plugin.bungeetags.common.Prefix;
import net.royalmind.minecraft.plugin.bungeetags.managers.PermissionManager;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TagCommand extends Command implements TabExecutor {

    private final PermissionManager permissionManager;
    private final Map<String, Prefix> prefixes;

    public TagCommand(final String name, final PermissionManager permissionManager, final Map<String, Prefix> prefixes) {
        super(name);
        this.permissionManager = permissionManager;
        this.prefixes = prefixes;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "It can only be executed by players!"));
            return;
        }
        if (args.length < 2) {
            sender.sendMessage(new TextComponent(ChatColor.YELLOW + "/tags [set/del] {tag name}"));
            return;
        }
        final String rootArg = args[0];
        final String tagName = args[1];
        final ProxiedPlayer proxiedPlayer = (ProxiedPlayer) sender;
        if (rootArg.equalsIgnoreCase("set")) {
            final Prefix prefix = this.prefixes.get(tagName);
            if (prefix == null) {
                sender.sendMessage(new TextComponent(ChatColor.RED + "Invalid tag!"));
                return;
            }
            final CompletableFuture<Boolean> futureSet = this.permissionManager.setPrefix(proxiedPlayer, prefix);
            futureSet.whenComplete((aBoolean, throwable) -> sender.sendMessage(new TextComponent(((aBoolean) ? ChatColor.GREEN : ChatColor.RED) + "Prefix added!")));
        } else if (rootArg.equalsIgnoreCase("del")) {
            final CompletableFuture<Boolean> futureDel = this.permissionManager.delPrefix(proxiedPlayer);
            futureDel.whenComplete((aBoolean, throwable) -> sender.sendMessage(new TextComponent(((aBoolean) ? ChatColor.GREEN : ChatColor.RED) + "Prefix deleted!")));

        } else {
            sender.sendMessage(new TextComponent(ChatColor.RED + "/tags [set/del] {tag name}"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(final CommandSender sender, final String[] args) {
        return null;
    }
}
