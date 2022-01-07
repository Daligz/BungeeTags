package net.royalmind.minecraft.plugin.bungeeprefix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.plugin.Plugin;
import net.royalmind.minecraft.plugin.bungeeprefix.adapters.UserAdapter;
import net.royalmind.minecraft.plugin.bungeeprefix.common.User;
import net.royalmind.minecraft.plugin.bungeeprefix.files.Configuration;

public final class BungeePrefix extends Plugin {

    @Getter
    private final Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(User.class, new UserAdapter())
            .setPrettyPrinting()
            .create();

    private Configuration configuration;

    @Override
    @SneakyThrows
    public void onEnable() {
        // Plugin startup logic
        this.loadFolder();
        this.configuration = new Configuration(this.gson, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadFolder() {
        this.getLogger().info((this.getDataFolder().mkdirs()) ? "Main folder created!" : "Main folder exists!");
    }
}
