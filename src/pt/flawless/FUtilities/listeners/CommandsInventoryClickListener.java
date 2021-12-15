package pt.flawless.FUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pt.flawless.FUtilities.api.Som;
import pt.flawless.FUtilities.inventories.CommandsMenu;
import pt.flawless.FUtilities.managers.VariablesManager;

public class CommandsInventoryClickListener extends VariablesManager implements Listener
{
    @EventHandler
    public void onCommmandsInventoryClick(InventoryClickEvent e)
    {
        if (e.getInventory().getTitle().equalsIgnoreCase(titulo))
        {
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(Material.AIR))
            {
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().getType().equals(Material.FEATHER))
            {
                verificarFly(e);
            }

            if (e.getCurrentItem().getType().equals(Material.WATER_BUCKET))
            {
                alterarChuva(e);
            }
            if (e.getCurrentItem().getType().equals(Material.PAPER))
            {
                limparChat(e);
            }
            if (e.getCurrentItem().getType().equals(Material.GOLDEN_CARROT))
            {
                healPlayer(e);
            }
        }
    }


    private void verificarFly(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Boolean flyState = player.getAllowFlight();

        if (!flyState)
        {
            player.setAllowFlight(true);
            player.sendMessage(flyOnMessage);
            CommandsMenu.load(player);
            Som.success(player);
        }
        else
        {
            player.setAllowFlight(false);
            player.sendMessage(flyOffMessage);
            CommandsMenu.load(player);
            Som.success(player);
        }
    }

    private void alterarChuva(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();
        Bukkit.getServer().dispatchCommand(player, "toggledownfall");
        Som.success(player);
    }

    private void limparChat(InventoryClickEvent e)
    {
        Player p = (Player) e.getWhoClicked();

        for (Player pl : Bukkit.getOnlinePlayers())
        {
            for (int i = 0; i <= 200; i++)
            {
                pl.sendMessage("");
            }
            pl.sendMessage(chatClearedMessage.replace("%chat_clear%", p.getName()));
            pl.sendMessage("");
            Som.success(p);
        }
    }

    private void healPlayer(InventoryClickEvent e)
    {
        Player player = (Player) e.getWhoClicked();

        player.setHealth(20.0);
        player.setFoodLevel(20);
        Som.success(player);

        player.sendMessage(healMessage);
    }
}
