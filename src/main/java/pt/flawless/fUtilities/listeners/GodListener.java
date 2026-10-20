package pt.flawless.fUtilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/** Jogadores em modo deus: não levam dano. O estado só vive em memória. */
public class GodListener implements Listener {
    private final Set<UUID> gods = new HashSet<>();

    /** Alterna o modo deus; devolve o novo estado. */
    public boolean toggle(Player p) {
        if (gods.remove(p.getUniqueId())) return false;
        gods.add(p.getUniqueId());
        return true;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p && gods.contains(p.getUniqueId())) e.setCancelled(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        gods.remove(e.getPlayer().getUniqueId());
    }
}
