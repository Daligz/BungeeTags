package net.royalmind.minecraft.plugin.bungeeprefix.files;

import com.google.gson.Gson;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

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
                    Arrays.asList("&7[TESTER]", "&c[MX]"),
                    this.gson
            );
            return;
        }
        final ArrayList<String> array = (ArrayList<String>) obj;
        array.forEach(s -> this.plugin.getLogger().info("element: " + s));
    }
}
