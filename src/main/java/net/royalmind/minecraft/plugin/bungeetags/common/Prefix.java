package net.royalmind.minecraft.plugin.bungeetags.common;

public interface Prefix {
    String getName();
    String getPrefix();
    String getPermission();
    String getRequiredPermission();
    void setName(final String name);
    void setPrefix(final String prefix);
    void setPermission(final String permission);
    void setRequiredPermission(final String requiredPermission);
}
