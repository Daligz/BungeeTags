package net.royalmind.minecraft.plugin.bungeetags.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrefixData implements Prefix {
    public static final String PREFIX_KEY = PrefixData.class.getSimpleName();
    private String name, prefix, permission, requiredPermission;
}
