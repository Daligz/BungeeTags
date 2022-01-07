package net.royalmind.minecraft.plugin.bungeeprefix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import net.royalmind.minecraft.plugin.bungeeprefix.adapters.PrefixAdapter;
import net.royalmind.minecraft.plugin.bungeeprefix.common.Prefix;
import net.royalmind.minecraft.plugin.bungeeprefix.files.Configuration;

import java.util.ArrayList;
import java.util.List;

public final class BungeePrefix extends Plugin {

    @Getter
    private final Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Prefix.class, new PrefixAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private final List<Prefix> prefixes = new ArrayList<>();
    private Configuration configuration;

    @Override
    @SneakyThrows
    public void onEnable() {
        // Plugin startup logic
        this.loadFolder();
        this.configuration = new Configuration(this.gson, this);
        this.prefixes.addAll(this.configuration.getPrefixes());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadFolder() {
        this.getLogger().info((this.getDataFolder().mkdirs()) ? "Main folder created!" : "Main folder exists!");
    }
}
