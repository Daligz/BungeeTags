package net.royalmind.minecraft.plugin.bungeeprefix.common;

public interface Prefix {
    String getPrefix();
    String getPermission();
    String getRequiredPermission();
    void setPrefix(final String prefix);
    void setPermission(final String permission);
    void setRequiredPermission(final String requiredPermission);
}
