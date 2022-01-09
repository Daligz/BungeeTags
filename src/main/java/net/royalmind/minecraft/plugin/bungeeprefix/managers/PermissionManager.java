package net.royalmind.minecraft.plugin.bungeeprefix.managers;

import lombok.AllArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.MetaNode;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.royalmind.minecraft.plugin.bungeeprefix.common.Prefix;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixData;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class PermissionManager {

    private final LuckPerms luckPerms;

    public boolean setPrefix(final ProxiedPlayer proxiedPlayer, final Prefix prefix) {
        final User user = this.getUser(proxiedPlayer);
        if (!(proxiedPlayer.hasPermission(prefix.getRequiredPermission()))) return false;
        if (!(proxiedPlayer.hasPermission(prefix.getPermission()))) return false;
        return this.matchNode(user, (result, error) -> {
            if (result != null) user.data().remove(result);
            user.data().add(MetaNode.builder(PrefixData.PREFIX_KEY, prefix.getPrefix()).build());
            this.luckPerms.getUserManager().saveUser(user);
        });
    }

    public boolean delPrefix(final ProxiedPlayer proxiedPlayer) {
        final User user = this.getUser(proxiedPlayer);
        return this.matchNode(user, (result, error) -> {
            if (result == null) return;
            user.data().remove(result);
            this.luckPerms.getUserManager().saveUser(user);
        });
    }

    public CompletableFuture<String> getPlayerPrefix(final ProxiedPlayer proxiedPlayer) {
        final User user = this.getUser(proxiedPlayer);
        final CompletableFuture<String> prefixFuture = new CompletableFuture<>();
        this.matchNode(user, (result, error) -> {
            if (result == null) {
                prefixFuture.complete(null);
                return;
            }
            prefixFuture.complete(result.getMetaValue());
        });
        return prefixFuture;
    }

    private User getUser(final ProxiedPlayer proxiedPlayer) {
        return this.luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(proxiedPlayer);
    }

    private boolean matchNode(final User user, final Callback<MetaNode> callback) {
        final boolean match = user.getNodes()
                .stream()
                .filter(node -> node instanceof MetaNode)
                .filter(node -> ((MetaNode) node).getMetaKey().equalsIgnoreCase(PrefixData.PREFIX_KEY))
                .anyMatch(node -> {
                    callback.done(((MetaNode) node), null);
                    return true;
                });
        if (!(match)) callback.done(null, new NullPointerException("Node not found."));
        return match;
    }
}