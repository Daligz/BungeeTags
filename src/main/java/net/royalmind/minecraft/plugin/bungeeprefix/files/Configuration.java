package net.royalmind.minecraft.plugin.bungeeprefix.files;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import net.md_5.bungee.api.plugin.Plugin;
import net.royalmind.minecraft.plugin.bungeeprefix.adapters.PrefixAdapter;
import net.royalmind.minecraft.plugin.bungeeprefix.common.Prefix;
import net.royalmind.minecraft.plugin.bungeeprefix.common.PrefixData;

import java.util.ArrayList;

public class Configuration {

    private final Gson gson;
    private final Plugin plugin;
    private final String prefixFile;

    private final static String PREFIX_FILE_NAME = "Prefix";

    public Configuration(final Gson gson, final Plugin plugin) {
        this.gson = gson;
        this.plugin = plugin;
        this.prefixFile = FileUtils.toServerFile(
                this.plugin.getDataFolder(), PREFIX_FILE_NAME, ".json"
        );
        this.load();
    }

    public void load() {
        final Object obj = this.getPrefixFile();
        if (obj == null) {
            FileUtils.saveFile(
                    this.prefixFile,
                    getDefaultJson(),
                    this.gson
            );
        }
    }

    public ArrayList<Prefix> getPrefixes() {
        final ArrayList<LinkedTreeMap<Object, Object>> prefixTree = (ArrayList<LinkedTreeMap<Object, Object>>) getPrefixFile();
        final ArrayList<Prefix> prefixes = new ArrayList<>();
        prefixTree.forEach(treeMap -> prefixes.add(new PrefixData(
                treeMap.get(PrefixAdapter.PREFIX).toString(),
                treeMap.get(PrefixAdapter.PERMISSION).toString(),
                treeMap.get(PrefixAdapter.REQUIRED_PERMISSION).toString()
        )));
        return prefixes;
    }

    private Object getPrefixFile() {
        return FileUtils.loadFile(
                this.prefixFile,
                new ArrayList<Prefix>(),
                this.gson
        );
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
