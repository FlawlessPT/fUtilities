package pt.flawless.fUtilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import pt.flawless.fUtilities.commands.VanishCommand;
import pt.flawless.fUtilities.menus.ColorsMenu;
import pt.flawless.fUtilities.menus.PlayerListMenu;

/** Cancela todos os cliques de {@code /cores} e {@code /lista}; trata as setas da lista. */
public class PlayerListMenuListener implements Listener {
    private final VanishCommand vanish;

    public PlayerListMenuListener(VanishCommand vanish) {
        this.vanish = vanish;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Object holder = e.getInventory().getHolder();
        if (holder instanceof ColorsMenu) {
            e.setCancelled(true);
            return;
        }
        if (!(holder instanceof PlayerListMenu menu)) return;
        e.setCancelled(true);
        if (e.getRawSlot() != e.getSlot() || !(e.getWhoClicked() instanceof Player p)) return;
        if (menu.isPrev(e.getRawSlot())) new PlayerListMenu(p, menu.page() - 1, vanish).open();
        else if (menu.isNext(e.getRawSlot())) new PlayerListMenu(p, menu.page() + 1, vanish).open();
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        Object holder = e.getInventory().getHolder();
        if (holder instanceof ColorsMenu || holder instanceof PlayerListMenu) e.setCancelled(true);
    }
}
