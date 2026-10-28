package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import pt.flawless.fapi.sounds.FSound;
import pt.flawless.fapi.vanish.VanishApi;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** {@code /vanish}: esconde o jogador de quem não tem {@code futilities.vanish.see}. O estado só vive em memória. Implementa o {@link VanishApi}. */
public class VanishCommand implements CommandExecutor, Listener, VanishApi {
    private static final String SEE_PERMISSION = "futilities.vanish.see";

    private final Plugin plugin;
    private final Set<UUID> vanished = new HashSet<>();

    public VanishCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isVanished(UUID uuid) {
        return vanished.contains(uuid);
    }

    @Override
    public void setVanished(Player p, boolean value) {
        if (value) {
            vanished.add(p.getUniqueId());
            for (Player other : Bukkit.getOnlinePlayers()) {
                if (!other.equals(p) && !other.hasPermission(SEE_PERMISSION)) other.hidePlayer(plugin, p);
            }
        } else {
            vanished.remove(p.getUniqueId());
            for (Player other : Bukkit.getOnlinePlayers()) other.showPlayer(plugin, p);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            p.sendMessage("§cUso: /vanish");
            return true;
        }
        if (isVanished(p.getUniqueId())) {
            setVanished(p, false);
            p.sendMessage("§cVanish desativado. Voltaste a ser visível.");
        } else {
            setVanished(p, true);
            p.sendMessage("§aVanish ativado. Estás invisível para os jogadores.");
        }
        FSound.success(p);
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player joined = e.getPlayer();
        if (joined.hasPermission(SEE_PERMISSION)) return;
        for (UUID id : vanished) {
            Player v = Bukkit.getPlayer(id);
            if (v != null) joined.hidePlayer(plugin, v);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        vanished.remove(e.getPlayer().getUniqueId());
    }
}
