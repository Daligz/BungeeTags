package net.royalmind.minecraft.plugin.bungeeprefix.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@Data
@AllArgsConstructor
public class PrefixUser implements User {

    private ProxiedPlayer proxiedPlayer;
    private String prefix, permission, requiredPermission;

    @Override
    public boolean hasPermission() {
        return this.proxiedPlayer.hasPermission(permission);
    }

    @Override
    public boolean hasRequiredPermission() {
        return this.proxiedPlayer.hasPermission(requiredPermission);
    }
}
