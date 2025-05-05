package pt.flawless.fUtilities;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fUtilities.commands.*;
import pt.flawless.fUtilities.listeners.CommandsInventoryClickListener;
import pt.flawless.fUtilities.listeners.FallDamageCancelListener;
import pt.flawless.fUtilities.listeners.GameModeChangeListener;

public class Main extends JavaPlugin {
    public static Plugin plugin;

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
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new FallDamageCancelListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandsInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameModeChangeListener(), this);
    }

    @Override
    public void onLoad() {
        reloadConfig();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        registerCommands();
        registerEvents();
        ConsoleCommandSender b = Bukkit.getConsoleSender();
        b.sendMessage("§e");
        b.sendMessage("§e[fUtilities] Plugin ativado com sucesso!");
        b.sendMessage("§e");
    }

    @Override
    public void onDisable() {
        ConsoleCommandSender b = Bukkit.getConsoleSender();
        b.sendMessage("§c");
        b.sendMessage("§c[fUtilities] Plugin desativado com sucesso!");
        b.sendMessage("§c");

    }
}
