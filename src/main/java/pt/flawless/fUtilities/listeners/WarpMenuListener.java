package pt.flawless.fUtilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import pt.flawless.fUtilities.menus.WarpMenu;
import pt.flawless.fUtilities.warps.Warp;
import pt.flawless.fUtilities.warps.WarpTeleporter;

public class WarpMenuListener implements Listener {
    private final WarpTeleporter teleporter;

    public WarpMenuListener(WarpTeleporter teleporter) {
        this.teleporter = teleporter;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof WarpMenu menu)) return;
        e.setCancelled(true);
        if (e.getRawSlot() != e.getSlot() || !(e.getWhoClicked() instanceof Player player)) return; // clique no inventário de baixo
        Warp warp = menu.warpAt(e.getRawSlot());
        if (warp == null) return;
        player.closeInventory();
        teleporter.teleport(player, warp.name());
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof WarpMenu) e.setCancelled(true);
    }
}
