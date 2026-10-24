package pt.flawless.fUtilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import pt.flawless.fUtilities.arenas.Arena;
import pt.flawless.fUtilities.arenas.ArenaStore;
import pt.flawless.fUtilities.arenas.ArenaTeleporter;
import pt.flawless.fUtilities.menus.ArenaMenu;

public class ArenaMenuListener implements Listener {
    private final ArenaStore store;

    public ArenaMenuListener(ArenaStore store) {
        this.store = store;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof ArenaMenu menu)) return;
        e.setCancelled(true);
        if (e.getRawSlot() != e.getSlot() || !(e.getWhoClicked() instanceof Player p)) return;
        Arena a = menu.arenaAt(e.getRawSlot());
        if (a == null) return;
        p.closeInventory();
        ArenaTeleporter.teleport(p, store, a.id());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof ArenaMenu) e.setCancelled(true);
    }
}
