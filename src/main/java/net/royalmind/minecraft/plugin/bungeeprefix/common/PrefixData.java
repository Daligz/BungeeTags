package net.royalmind.minecraft.plugin.bungeeprefix.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrefixData implements Prefix {
    private String prefix, permission, requiredPermission;
}
