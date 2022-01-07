package net.royalmind.minecraft.plugin.bungeeprefix.adapters;

import com.google.gson.*;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixUser;

import java.lang.reflect.Type;

public class UserAdapter implements JsonDeserializer<PrefixUser>, JsonSerializer<PrefixUser> {

    @Override
    public PrefixUser deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(final PrefixUser src, final Type typeOfSrc, final JsonSerializationContext context) {
        return null;
    }
}
