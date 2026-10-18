package pt.flawless.fUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import pt.flawless.fUtilities.warps.ReplaceConfirmations;
import pt.flawless.fUtilities.warps.Warp;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fapi.sounds.FSound;

import java.util.Optional;
import java.util.UUID;

/** Apanha a resposta ao aviso do /setwarp antes de qualquer outro listener de chat. */
public class ReplaceConfirmListener implements Listener {
    private final Plugin plugin;
    private final WarpStore store;
    private final ReplaceConfirmations confirmations;

    public ReplaceConfirmListener(Plugin plugin, WarpStore store, ReplaceConfirmations confirmations) {
        this.plugin = plugin;
        this.store = store;
        this.confirmations = confirmations;
    }

    // Sem ignoreCancelled, para correr primeiro e esconder a resposta do chat público.
    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        UUID id = e.getPlayer().getUniqueId();
        Optional<Warp> req = confirmations.take(id);
        if (req.isEmpty()) return;
        e.setCancelled(true);
        boolean yes = ReplaceConfirmations.isYes(e.getMessage());
        Bukkit.getScheduler().runTask(plugin, () -> {
            Player p = Bukkit.getPlayer(id);
            if (p == null) return;
            if (!yes) {
                p.sendMessage("§cCancelado.");
                FSound.fail(p);
                return;
            }
            try {
                store.save(req.get());
            } catch (RuntimeException ex) {
                p.sendMessage("§cErro ao guardar o warp: " + ex.getMessage());
                FSound.fail(p);
                return;
            }
            p.sendMessage("§aWarp " + req.get().name() + " substituído.");
            FSound.success(p);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        confirmations.cancel(e.getPlayer().getUniqueId());
    }
}
