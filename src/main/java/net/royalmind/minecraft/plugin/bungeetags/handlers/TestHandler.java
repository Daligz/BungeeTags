package net.royalmind.minecraft.plugin.bungeetags.handlers;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.royalmind.minecraft.plugin.bungeetags.managers.PermissionManager;

@AllArgsConstructor
public class TestHandler implements Listener {

    private final PermissionManager permissionManager;

    @EventHandler
    public void onChat(final ChatEvent event) {
        final String message = event.getMessage();
        if (message.startsWith("/")) return;
        final ProxiedPlayer proxiedPlayer = (ProxiedPlayer) event.getSender();
        event.setCancelled(true);
        this.permissionManager.getPlayerPrefix(proxiedPlayer).whenComplete((s, throwable) -> {
            if (s == null) {
                ProxyServer.getInstance().broadcast(new TextComponent(message));
                return;
            }
            ProxyServer.getInstance().broadcast(new TextComponent(translate(s) + message));
        });
    }

    private String translate(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
