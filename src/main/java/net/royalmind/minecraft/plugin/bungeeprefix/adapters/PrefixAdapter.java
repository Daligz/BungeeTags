package net.royalmind.minecraft.plugin.bungeeprefix.adapters;

import com.google.gson.*;
import net.royalmind.minecraft.plugin.bungeeprefix.common.Prefix;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixData;

import java.lang.reflect.Type;

public class PrefixAdapter implements JsonDeserializer<Prefix>, JsonSerializer<Prefix> {

    public static final String NAME = "Name", PREFIX = "Prefix", PERMISSION = "Permission", REQUIRED_PERMISSION = "RequiredPermission";

    @Override
    public Prefix deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
        if (json == null || !(json.isJsonObject())) return null;
        final JsonObject jsonObject = json.getAsJsonObject();
        return new PrefixData(
                jsonObject.get(NAME).getAsString(),
                jsonObject.get(PREFIX).getAsString(),
                jsonObject.get(PERMISSION).getAsString(),
                jsonObject.get(REQUIRED_PERMISSION).getAsString()
        );
    }

    @Override
    public JsonElement serialize(final Prefix src, final Type typeOfSrc, final JsonSerializationContext context) {
        if (src == null) return null;
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(NAME, src.getName());
        jsonObject.addProperty(PREFIX, src.getPrefix());
        jsonObject.addProperty(PERMISSION, src.getPermission());
        jsonObject.addProperty(REQUIRED_PERMISSION, src.getRequiredPermission());
        return jsonObject;
    }
}
