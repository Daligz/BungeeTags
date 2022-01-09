package net.royalmind.minecraft.plugin.bungeeprefix.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrefixData implements Prefix {
    public static final String PREFIX_KEY = PrefixData.class.getSimpleName();
    private String prefix, permission, requiredPermission;
}
