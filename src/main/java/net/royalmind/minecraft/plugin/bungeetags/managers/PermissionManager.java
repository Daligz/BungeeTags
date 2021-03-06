package net.royalmind.minecraft.plugin.bungeetags.managers;

import lombok.AllArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.MetaNode;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.royalmind.minecraft.plugin.bungeetags.common.Prefix;
import net.royalmind.minecraft.plugin.bungeetags.common.PrefixData;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class PermissionManager {

    private final LuckPerms luckPerms;

    public CompletableFuture<Boolean> setPrefix(final ProxiedPlayer proxiedPlayer, final Prefix prefix) {
        final CompletableFuture<Boolean> future = new CompletableFuture<>();
        final User user = this.getUser(proxiedPlayer);
        if (!(proxiedPlayer.hasPermission(prefix.getRequiredPermission()))) future.complete(false);
        if (!(proxiedPlayer.hasPermission(prefix.getPermission()))) future.complete(false);
        this.matchNode(user, (result, error) -> {
            if (result != null) user.data().remove(result);
            user.data().add(MetaNode.builder(PrefixData.PREFIX_KEY, prefix.getPrefix()).build());
            this.luckPerms.getUserManager().saveUser(user);
            future.complete(true);
        });
        return future;
    }

    public CompletableFuture<Boolean> delPrefix(final ProxiedPlayer proxiedPlayer) {
        final CompletableFuture<Boolean> future = new CompletableFuture<>();
        final User user = this.getUser(proxiedPlayer);
        this.matchNode(user, (result, error) -> {
            if (result == null) {
                future.complete(false);
                return;
            }
            user.data().remove(result);
            this.luckPerms.getUserManager().saveUser(user);
            future.complete(true);
        });
        return future;
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