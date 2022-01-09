package net.royalmind.minecraft.plugin.bungeetags;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.SneakyThrows;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.royalmind.minecraft.plugin.bungeetags.adapters.PrefixAdapter;
import net.royalmind.minecraft.plugin.bungeetags.commands.TagCommand;
import net.royalmind.minecraft.plugin.bungeetags.common.Prefix;
import net.royalmind.minecraft.plugin.bungeetags.files.Configuration;
import net.royalmind.minecraft.plugin.bungeetags.handlers.TestHandler;
import net.royalmind.minecraft.plugin.bungeetags.managers.PermissionManager;

import java.util.HashMap;
import java.util.Map;

public final class BungeeTags extends Plugin {

    @Getter
    private final Gson gson = new GsonBuilder()
            .registerTypeHierarchyAdapter(Prefix.class, new PrefixAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private final Map<String, Prefix> prefixes = new HashMap<>();
    private Configuration configuration;
    private PermissionManager permissionManager;

    @Override
    @SneakyThrows
    public void onEnable() {
        // Plugin startup logic
        final LuckPerms luckPerms = LuckPermsProvider.get();
        this.permissionManager = new PermissionManager(luckPerms);
        this.loadFolder();
        this.configuration = new Configuration(this.gson, this);
        this.loadPrefixes();
        this.loadCommands(new TagCommand("tags", permissionManager, prefixes));
        this.getProxy().getPluginManager().registerListener(this, new TestHandler(this.permissionManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void loadPrefixes() {
        this.configuration.getPrefixes().forEach(prefix -> this.prefixes.put(prefix.getName(), prefix));
    }

    private void loadCommands(final Command... commands) {
        for (final Command command : commands) this.getProxy().getPluginManager().registerCommand(this, command);
    }

    private void loadFolder() {
        this.getLogger().info((this.getDataFolder().mkdirs()) ? "Main folder created!" : "Main folder exists!");
    }
}
