package pt.flawless.fUtilities;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fUtilities.commands.*;
import pt.flawless.fUtilities.listeners.CommandsInventoryClickListener;
import pt.flawless.fUtilities.listeners.GameModeChangeListener;
import pt.flawless.fUtilities.listeners.PrefsInventoryListener;
import pt.flawless.fapi.chat.ChatApi;
import pt.flawless.fapi.chat.Chats;
import pt.flawless.fapi.enums.EMessageTypeEnum;
import pt.flawless.fapi.logs.FConsoleLogger;

public class FUtilities extends JavaPlugin {
    private static Plugin plugin;
    FConsoleLogger consoleLogger = new FConsoleLogger(this.getName());

    private ChatApi chat;

    private void registerCommands() {
        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("tp").setExecutor(new TpCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("comandos").setExecutor(new CommandsCommand());
        getCommand("tphere").setExecutor(new TphereCommand());
        getCommand("vida").setExecutor(new VidaCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("day").setExecutor(new DayNightCommand());
        getCommand("night").setExecutor(new DayNightCommand());
        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("prefs").setExecutor(new PrefsCommand(chat));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CommandsInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameModeChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PrefsInventoryListener(this), this);
    }

    @Override
    public void onLoad() {
        reloadConfig();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;

        try {
            chat = Chats.get();
        } catch (RuntimeException e) {
            consoleLogger.sendMessage("/prefs indisponível: " + e.getMessage(), EMessageTypeEnum.ERROR);
        }

        registerCommands();
        registerEvents();

        consoleLogger.sendEnablePluginMessage();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll();

        consoleLogger.sendDisablePluginMessage();
    }

    public static Plugin getMainPlugin() {
        return plugin;
    }
}
