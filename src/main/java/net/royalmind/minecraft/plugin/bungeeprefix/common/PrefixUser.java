package net.royalmind.minecraft.plugin.bungeeprefix.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.annotation.Nullable;

@Data
@AllArgsConstructor
public class PrefixUser implements User {

    private @Nullable ProxiedPlayer proxiedPlayer;
    private String prefix, permission, requiredPermission;

    @Override
    public boolean hasPermission() {
        return (this.proxiedPlayer != null && this.proxiedPlayer.hasPermission(this.permission));
    }

    @Override
    public boolean hasRequiredPermission() {
        return (this.proxiedPlayer != null && this.proxiedPlayer.hasPermission(this.requiredPermission));
    }
}
