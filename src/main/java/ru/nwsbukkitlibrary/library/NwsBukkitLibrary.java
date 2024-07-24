package ru.nwsbukkitlibrary.library;

import org.bukkit.plugin.java.JavaPlugin;

public final class NwsBukkitLibrary extends JavaPlugin {

    private static NwsBukkitLibrary instance;

    @Override
    public void onEnable() {
        instance = this;

    }

    public static NwsBukkitLibrary getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
