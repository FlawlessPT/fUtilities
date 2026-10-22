package pt.flawless.fUtilities.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import pt.flawless.fUtilities.chests.ChestStore;
import pt.flawless.fUtilities.chests.PersonalChest;

import java.io.IOException;

/** Grava o baú pessoal quando o jogador o fecha ou sai do servidor. */
public class PersonalChestListener implements Listener {
    private final Plugin plugin;
    private final ChestStore store;

    public PersonalChestListener(Plugin plugin, ChestStore store) {
        this.plugin = plugin;
        this.store = store;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        save(e.getInventory());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.getOpenInventory() != null) save(p.getOpenInventory().getTopInventory());
    }

    private void save(Inventory inv) {
        if (!(inv.getHolder() instanceof PersonalChest chest)) return;
        try {
            store.save(chest.getOwner(), chest.getNumber(), inv.getContents());
        } catch (IOException ex) {
            plugin.getLogger().severe("Falha ao gravar o baú " + chest.getNumber() + " de " + chest.getOwner() + ": " + ex);
        }
    }
}
