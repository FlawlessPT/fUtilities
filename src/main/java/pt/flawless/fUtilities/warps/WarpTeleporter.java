package pt.flawless.fUtilities.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import pt.flawless.fapi.permissions.GroupInfo;
import pt.flawless.fapi.permissions.PermissionsApi;
import pt.flawless.fapi.sounds.FSound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/** Teleporte para warps com atraso. {@code perms} é nulo se o fPermissions não estiver disponível. */
public class WarpTeleporter implements Listener {
    private final Plugin plugin;
    private final WarpStore store;
    private final PermissionsApi perms;
    private final Map<UUID, BukkitTask> pending = new HashMap<>();

    public WarpTeleporter(Plugin plugin, WarpStore store, PermissionsApi perms) {
        this.plugin = plugin;
        this.store = store;
        this.perms = perms;
    }

    /** Chamar na thread principal. */
    public void teleport(Player player, String warpName) {
        Optional<Warp> found = store.get(warpName);
        if (found.isEmpty() || !found.get().active()) {
            fail(player, "Esse warp não existe.");
            return;
        }
        Warp warp = found.get();
        if (Bukkit.getWorld(warp.world()) == null) {
            fail(player, "Mundo indisponível.");
            return;
        }
        String group = warp.group();
        if (group == null || group.isEmpty()) {
            start(player, warp);
        } else if (perms == null) {
            fail(player, "Não tens acesso a este warp.");
        } else {
            UUID id = player.getUniqueId();
            perms.getGroups(id).whenComplete((groups, err) -> Bukkit.getScheduler().runTask(plugin, () -> {
                Player online = Bukkit.getPlayer(id);
                if (online == null) return;
                if (err != null || !WarpAccess.allowed(group, groups, name ->
                        perms.getGroup(name).map(GroupInfo::inherits).orElse(List.of()))) {
                    fail(online, "Não tens acesso a este warp.");
                } else {
                    start(online, warp);
                }
            }));
        }
    }

    private void start(Player player, Warp warp) {
        int delay = plugin.getConfig().getInt("Warp.Delay", 3);
        UUID id = player.getUniqueId();
        BukkitTask old = pending.remove(id);
        if (old != null) old.cancel();
        // Fechar o menu e teleportar fora do InventoryClickEvent.
        if (delay <= 0 || player.hasPermission("futilities.warp.bypass")) {
            Bukkit.getScheduler().runTask(plugin, () -> {
                Player online = Bukkit.getPlayer(id);
                if (online == null) return;
                online.closeInventory();
                go(online, warp);
            });
            return;
        }
        Bukkit.getScheduler().runTask(plugin, player::closeInventory);
        int[] remaining = {delay};
        // Uma mensagem por segundo (delay, ..., 1); ao fim do último segundo teleporta.
        pending.put(id, Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Player online = Bukkit.getPlayer(id);
            if (online == null) {
                cancelPending(id);
                return;
            }
            if (remaining[0] > 0) {
                online.sendMessage("§aTeleporte em §e" + remaining[0] + "§a...");
                remaining[0]--;
                return;
            }
            cancelPending(id);
            go(online, warp);
        }, 0L, 20L));
    }

    private void go(Player player, Warp warp) {
        World world = Bukkit.getWorld(warp.world());
        if (world == null) {
            fail(player, "Mundo indisponível.");
            return;
        }
        player.teleport(new Location(world, warp.x(), warp.y(), warp.z(), warp.yaw(), warp.pitch()));
        FSound.success(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        cancelPending(e.getPlayer().getUniqueId());
    }

    private void cancelPending(UUID id) {
        BukkitTask t = pending.remove(id);
        if (t != null) t.cancel();
    }

    private void fail(Player player, String msg) {
        player.sendMessage("§c" + msg);
        FSound.fail(player);
    }
}
