package net.royalmind.minecraft.plugin.bungeeprefix.files;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class Configuration {

    private final Gson gson;
    private final Plugin plugin;

    private final static String PREFIX_FILE_NAME = "Prefix";

    public Configuration(final Gson gson, final Plugin plugin) throws FileNotFoundException {
        this.gson = gson;
        this.plugin = plugin;
        this.load();
    }

    public void load() throws FileNotFoundException {
        final File dataFolder = this.plugin.getDataFolder();
        final Object obj = FileUtils.loadFile(
                FileUtils.toServerFile(
                        dataFolder, PREFIX_FILE_NAME, ".json"
                ),
                new ArrayList<String>(),
                this.gson
        );
        final String json = this.gson.toJson(Arrays.asList("", ""));
    }
}
