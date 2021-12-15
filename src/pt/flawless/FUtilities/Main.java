package pt.flawless.FUtilities;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.FUtilities.commands.*;
import pt.flawless.FUtilities.listeners.CommandsInventoryClickListener;
import pt.flawless.FUtilities.listeners.FallDamageCancelListener;

public class Main extends JavaPlugin
{

    public static Plugin plugin;

    private void registerCommands()
    {
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("tp").setExecutor(new TpCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("comandos").setExecutor(new CommandsCommand());
    }

    private void registerEvents()
    {
        Bukkit.getPluginManager().registerEvents(new FallDamageCancelListener(), this);
        Bukkit.getPluginManager().registerEvents(new CommandsInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GamemodeCommand(), this);
    }

    @Override
    public void onLoad()
    {
        reloadConfig();
    }

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        plugin = this;
        registerCommands();
        registerEvents();
        ConsoleCommandSender b = Bukkit.getConsoleSender();
        b.sendMessage("§e");
        b.sendMessage("§e[FUtilities] Plugin ativado com sucesso!");
        b.sendMessage("§e");
    }

    @Override
    public void onDisable()
    {
        ConsoleCommandSender b = Bukkit.getConsoleSender();
        b.sendMessage("§c");
        b.sendMessage("§c[FUtilities] Plugin desativado com sucesso!");
        b.sendMessage("§c");

    }
}
