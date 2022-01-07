package net.royalmind.minecraft.plugin.bungeeprefix.files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.md_5.bungee.api.plugin.Plugin;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixData;

import java.io.File;
import java.util.ArrayList;

public class Configuration {

    private final Gson gson;
    private final Plugin plugin;

    private final static String PREFIX_FILE_NAME = "Prefix";

    public Configuration(final Gson gson, final Plugin plugin) {
        this.gson = gson;
        this.plugin = plugin;
        this.load();
    }

    public void load() {
        final File dataFolder = this.plugin.getDataFolder();
        final String prefixFile = FileUtils.toServerFile(
                dataFolder, PREFIX_FILE_NAME, ".json"
        );
        final Object obj = FileUtils.loadFile(
                prefixFile,
                new ArrayList<String>(),
                this.gson
        );
        if (obj == null) {
            FileUtils.saveFile(
                    prefixFile,
                    getDefaultJson(),
                    this.gson
            );
        }
    }

    private JsonArray getDefaultJson() {
        final JsonElement userJsonElement = this.gson.toJsonTree(
                new PrefixData(
                        "&9[TESTER]",
                        "prefix.tester",
                        "prefix.required.vip"
                )
        );
        final JsonArray jsonElements = new JsonArray();
        jsonElements.add(userJsonElement);
        return jsonElements;
    }
}
