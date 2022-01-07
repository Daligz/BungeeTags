package net.royalmind.minecraft.plugin.bungeeprefix.adapters;

import com.google.gson.*;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixUser;
import net.royalmind.minecraft.plugin.bungeeprefix.common.User;

import java.lang.reflect.Type;

public class UserAdapter implements JsonDeserializer<User>, JsonSerializer<User> {

    public static final String PREFIX = "Prefix", PERMISSION = "Permission", REQUIRED_PERMISSION = "RequiredPermission";

    @Override
    public User deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !(json.isJsonObject())) return null;
        final JsonObject jsonObject = json.getAsJsonObject();
        return new PrefixUser(
                null,
                jsonObject.get(PREFIX).getAsString(),
                jsonObject.get(PERMISSION).getAsString(),
                jsonObject.get(REQUIRED_PERMISSION).getAsString()
        );
    }

    @Override
    public JsonElement serialize(final User src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src == null) return null;
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(PREFIX, src.getPrefix());
        jsonObject.addProperty(PERMISSION, src.getPermission());
        jsonObject.addProperty(REQUIRED_PERMISSION, src.getRequiredPermission());
        return jsonObject;
    }
}
