package pt.flawless.FUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pt.flawless.FUtilities.inventories.CommandsMenu;
import pt.flawless.FUtilities.managers.VariablesManager;
import pt.flawless.fapi.sounds.FSound;

public class CommandsInventoryClickListener extends VariablesManager implements Listener {
    @EventHandler
    public void onCommandsMenuInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase(titulo)) {
            e.setCancelled(true);

            if (e.getCurrentItem().getType().equals(Material.AIR)) {
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().getType().equals(Material.FEATHER)) {
                checkFly(e);
            }

            if (e.getCurrentItem().getType().equals(Material.WATER_BUCKET)) {
                changeRain(e);
            }

            if (e.getCurrentItem().getType().equals(Material.PAPER)) {
                clearChat(e);
            }

            if (e.getCurrentItem().getType().equals(Material.GOLDEN_CARROT)) {
                healPlayer(e);
            }
        }
    }


    private void checkFly(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Boolean flyState = player.getAllowFlight();

        if (!flyState) {
            player.setAllowFlight(true);
            player.sendMessage(flyOnMessage);
        } else {
            player.setAllowFlight(false);
            player.sendMessage(flyOffMessage);
        }
        CommandsMenu.load(player);
        FSound.success(player);
    }

    private void changeRain(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Bukkit.getServer().dispatchCommand(player, "toggledownfall");
        FSound.success(player);
    }

    private void clearChat(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        // logic is reused in command. Separate in external file
        for (Player pl : Bukkit.getOnlinePlayers()) {
            for (int i = 0; i <= 200; i++) {
                pl.sendMessage("");
            }

            pl.sendMessage(chatClearedMessage.replace("%chat_clear%", p.getName()));
            pl.sendMessage("");

            FSound.success(p);
        }
    }

    private void healPlayer(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        player.setHealth(20.0);
        player.setFoodLevel(20);

        FSound.success(player);

        player.sendMessage(healMessage);
    }
}
