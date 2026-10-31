package pt.flawless.fUtilities;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fUtilities.commands.*;
import pt.flawless.fUtilities.listeners.CommandsInventoryClickListener;
import pt.flawless.fUtilities.listeners.GameModeChangeListener;
import pt.flawless.fUtilities.listeners.GodListener;
import pt.flawless.fUtilities.listeners.PersonalChestListener;
import pt.flawless.fUtilities.chests.ChestStore;
import pt.flawless.fUtilities.listeners.ReplaceConfirmListener;
import pt.flawless.fUtilities.listeners.WarpMenuListener;
import pt.flawless.fUtilities.arenas.ArenaStore;
import pt.flawless.fUtilities.commands.ArenaCommand;
import pt.flawless.fUtilities.commands.TpallCommand;
import pt.flawless.fUtilities.commands.VanishCommand;
import pt.flawless.fUtilities.commands.TellCommand;
import pt.flawless.fUtilities.commands.TpaCommand;
import pt.flawless.fUtilities.commands.ArenasCommand;
import pt.flawless.fUtilities.commands.SetArenaCommand;
import pt.flawless.fUtilities.listeners.ArenaMenuListener;
import pt.flawless.fUtilities.warps.ReplaceConfirmations;
import org.bukkit.plugin.ServicePriority;
import pt.flawless.fapi.vanish.VanishApi;
import pt.flawless.fapi.friends.Friends;
import pt.flawless.fapi.friends.FriendsApi;
import pt.flawless.fapi.enums.EMessageTypeEnum;
import pt.flawless.fapi.logs.FConsoleLogger;
import pt.flawless.fapi.permissions.Permissions;
import pt.flawless.fapi.permissions.PermissionsApi;
import pt.flawless.fUtilities.homes.HomeStore;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fUtilities.warps.WarpTeleporter;

import java.io.File;

public class FUtilities extends JavaPlugin {
    private static Plugin plugin;
    FConsoleLogger consoleLogger = new FConsoleLogger(this.getName());

    private FriendsApi friends;
    private PermissionsApi perms;
    private WarpTeleporter teleporter;
    private WarpStore warps;
    private ArenaStore arenas;
    private final GodListener god = new GodListener();
    private ChestStore chests;
    private final ReplaceConfirmations confirmations = new ReplaceConfirmations(System::currentTimeMillis, 15000);

    private void registerCommands() {
        getCommand("gamemode").setExecutor(new GameModeCommand());
        getCommand("tp").setExecutor(new TpCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("clearchat").setExecutor(new ClearChatCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("god").setExecutor(new GodCommand(god));
        getCommand("stack").setExecutor(new StackCommand());
        VanishCommand vanish = new VanishCommand(this);
        getCommand("vanish").setExecutor(vanish);
        Bukkit.getPluginManager().registerEvents(vanish, this);
        Bukkit.getServicesManager().register(VanishApi.class, vanish, this, ServicePriority.Normal);
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("lixo").setExecutor(new LixoCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("luz").setExecutor(new LuzCommand());
        getCommand("online").setExecutor(new OnlineCommand(vanish));
        getCommand("cores").setExecutor(new CoresCommand());
        getCommand("lista").setExecutor(new ListaCommand(vanish));
        Bukkit.getPluginManager().registerEvents(new pt.flawless.fUtilities.listeners.PlayerListMenuListener(vanish), this);
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("top").setExecutor(new TopCommand());
        pt.flawless.fUtilities.commands.FreezeCommand freeze = new pt.flawless.fUtilities.commands.FreezeCommand();
        getCommand("freeze").setExecutor(freeze);
        Bukkit.getPluginManager().registerEvents(freeze, this);
        getCommand("head").setExecutor(new HeadCommand());
        getCommand("summon").setExecutor(new SummonCommand());
        getCommand("mobkill").setExecutor(new MobKillCommand());
        getCommand("estatisticas").setExecutor(new EstatisticasCommand());
        TellCommand tell = new TellCommand();
        getCommand("tell").setExecutor(tell);
        getCommand("r").setExecutor(tell);
        Bukkit.getPluginManager().registerEvents(tell, this);
        TpaCommand tpa = new TpaCommand(friends);
        getCommand("tpa").setExecutor(tpa);
        getCommand("tpaccept").setExecutor(tpa);
        getCommand("tpdeny").setExecutor(tpa);
        getCommand("iteminfo").setExecutor(new ItemInfoCommand());
        chests = new ChestStore(getDataFolder());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("bau").setExecutor(new BauCommand(chests));
        getCommand("comandos").setExecutor(new CommandsCommand());
        getCommand("tphere").setExecutor(new TphereCommand());
        getCommand("tpall").setExecutor(new TpallCommand());
        getCommand("vida").setExecutor(new VidaCommand());
        getCommand("invsee").setExecutor(new InvseeCommand());
        getCommand("day").setExecutor(new DayNightCommand());
        getCommand("night").setExecutor(new DayNightCommand());
        getCommand("clear").setExecutor(new ClearCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());

        warps = new WarpStore(new File(getDataFolder(), "warps.yml"));
        arenas = new ArenaStore(new File(getDataFolder(), "arenas.yml"));
        getCommand("arena").setExecutor(new ArenaCommand(arenas));
        getCommand("arenas").setExecutor(new ArenasCommand(arenas));
        getCommand("setarena").setExecutor(new SetArenaCommand(arenas));
        HomeStore homes = new HomeStore(new File(getDataFolder(), "homes.yml"));
        getCommand("sethome").setExecutor(new SetHomeCommand(this, homes));
        getCommand("delhome").setExecutor(new DelHomeCommand(homes));
        getCommand("home").setExecutor(new HomeCommand(homes));
        getCommand("homes").setExecutor(new HomesCommand(homes));
        teleporter = new WarpTeleporter(this, warps, perms);
        getCommand("warp").setExecutor(new WarpCommand(teleporter, warps));
        getCommand("setwarp").setExecutor(new SetWarpCommand(warps, confirmations));
        getCommand("delwarp").setExecutor(new DelWarpCommand(warps));
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new CommandsInventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new GameModeChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(god, this);
        Bukkit.getPluginManager().registerEvents(new pt.flawless.fUtilities.listeners.LixoListener(), this);
        Bukkit.getPluginManager().registerEvents(new PersonalChestListener(this, chests), this);
        Bukkit.getPluginManager().registerEvents(new WarpMenuListener(teleporter), this);
        Bukkit.getPluginManager().registerEvents(new ArenaMenuListener(arenas), this);
        Bukkit.getPluginManager().registerEvents(teleporter, this);
        Bukkit.getPluginManager().registerEvents(new pt.flawless.fUtilities.listeners.WarpAliasListener(warps, teleporter), this);
        Bukkit.getPluginManager().registerEvents(new ReplaceConfirmListener(this, warps, confirmations), this);
    }

    @Override
    public void onLoad() {
        reloadConfig();
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        plugin = this;

        if (getServer().getPluginManager().getPlugin("fFriends") != null) {
            try {
                friends = Friends.get();
            } catch (RuntimeException e) {
                consoleLogger.sendMessage("Tp automático entre amigos indisponível: " + e.getMessage(), EMessageTypeEnum.ERROR);
            }
        }

        try {
            perms = Permissions.get();
        } catch (RuntimeException e) {
            consoleLogger.sendMessage("Warps com grupo indisponíveis: " + e.getMessage(), EMessageTypeEnum.ERROR);
        }

        registerCommands();
        registerEvents();

        consoleLogger.sendEnablePluginMessage();
    }

    @Override
    public void onDisable() {
        // fecha os baús abertos (grava-os) antes de largar os listeners
        for (org.bukkit.entity.Player online : Bukkit.getOnlinePlayers()) {
            if (online.getOpenInventory().getTopInventory().getHolder() instanceof pt.flawless.fUtilities.chests.PersonalChest) online.closeInventory();
        }
        HandlerList.unregisterAll();

        consoleLogger.sendDisablePluginMessage();
    }

    public static Plugin getMainPlugin() {
        return plugin;
    }
}
