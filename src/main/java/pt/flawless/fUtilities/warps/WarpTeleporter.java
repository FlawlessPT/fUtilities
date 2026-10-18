package pt.flawless.fUtilities.warps;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
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
public class WarpTeleporter {
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
        if (delay <= 0 || player.hasPermission("futilities.warp.bypass")) {
            go(player, warp);
            return;
        }
        player.sendMessage("§aTeleporte em " + delay + " segundos.");
        pending.put(id, Bukkit.getScheduler().runTaskLater(plugin, () -> {
            pending.remove(id);
            Player online = Bukkit.getPlayer(id);
            if (online != null) go(online, warp);
        }, delay * 20L));
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

    private void fail(Player player, String msg) {
        player.sendMessage("§c" + msg);
        FSound.fail(player);
    }
}
