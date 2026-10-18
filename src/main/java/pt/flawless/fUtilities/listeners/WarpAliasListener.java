package pt.flawless.fUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pt.flawless.fUtilities.warps.Warp;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fUtilities.warps.WarpTeleporter;

/** {@code /<alias>} equivale a {@code /warp <nome>}; um comando de plugin com o mesmo nome tem prioridade. */
public class WarpAliasListener implements Listener {
    private final WarpStore store;
    private final WarpTeleporter teleporter;

    public WarpAliasListener(WarpStore store, WarpTeleporter teleporter) {
        this.store = store;
        this.teleporter = teleporter;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        String word = msg.startsWith("/") ? msg.substring(1) : msg;
        word = word.split(" ", 2)[0];
        if (word.isEmpty() || Bukkit.getPluginCommand(word) != null) return;
        Warp warp;
        try {
            warp = store.findByAlias(word).orElse(null);
        } catch (RuntimeException ex) {
            return; // ficheiro ilegível: ignora o alias
        }
        if (warp == null) return;
        e.setCancelled(true);
        teleporter.teleport(e.getPlayer(), warp.name());
    }
}
