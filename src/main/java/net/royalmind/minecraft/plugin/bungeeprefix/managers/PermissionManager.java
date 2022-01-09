package net.royalmind.minecraft.plugin.bungeeprefix.managers;

import lombok.AllArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.MetaNode;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.royalmind.minecraft.plugin.bungeeprefix.common.Prefix;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixData;

@AllArgsConstructor
public class PermissionManager {

    private final LuckPerms luckPerms;

    public boolean setPrefix(final ProxiedPlayer proxiedPlayer, final Prefix prefix) {
        final User user = this.luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(proxiedPlayer);
        if (!(proxiedPlayer.hasPermission(prefix.getRequiredPermission()))) return false;
        if (!(proxiedPlayer.hasPermission(prefix.getPermission()))) return false;
        final boolean match = this.matchNode(user, (result, error) -> {
            user.data().remove(result);
            user.data().add(MetaNode.builder(PrefixData.PREFIX_KEY, prefix.getPrefix()).build());
        });
        return this.saveUser(match, user);
    }

    public boolean delPrefix(final ProxiedPlayer proxiedPlayer, final Prefix prefix) {
        final User user = this.luckPerms.getPlayerAdapter(ProxiedPlayer.class).getUser(proxiedPlayer);
        final boolean match = this.matchNode(user, (result, error) -> user.data().remove(result));
        return this.saveUser(match, user);
    }

    private boolean saveUser(final boolean match, final User user) {
        if (match) this.luckPerms.getUserManager().saveUser(user);
        return match;
    }

    private boolean matchNode(final User user, final Callback<MetaNode> callback) {
        return user.getNodes()
                .stream()
                .filter(node -> node instanceof MetaNode)
                .filter(node -> ((MetaNode) node).getMetaKey().equalsIgnoreCase(PrefixData.PREFIX_KEY))
                .anyMatch(node -> {
                    callback.done(((MetaNode) node), null);
                    return true;
                });
    }
}