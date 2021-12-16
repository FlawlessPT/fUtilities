package pt.flawless.FUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pt.flawless.FUtilities.inventories.CommandsInventory;
import pt.flawless.FUtilities.managers.VariablesManager;
import pt.flawless.FUtilities.utils.SoundEffect;

public class CommandsInventoryClickListener extends VariablesManager implements Listener
{
    private Player player;
    private InventoryClickEvent inventoryClickEvent;

    @EventHandler
    public void onCommandsInventoryClick(InventoryClickEvent e)
    {
        if (e.getInventory().getTitle().equalsIgnoreCase(titulo))
        {
            e.setCancelled(true);
            this.player = (Player) e.getWhoClicked();

            if (e.getCurrentItem().getType().equals(Material.AIR))
            {
                e.setCancelled(true);
                return;
            }

            if (e.getCurrentItem().getType().equals(Material.FEATHER))
            {
                updateFly();
            }

            if (e.getCurrentItem().getType().equals(Material.WATER_BUCKET))
            {
                updateWeather();
            }
            if (e.getCurrentItem().getType().equals(Material.PAPER))
            {
                clearChat();
            }
            if (e.getCurrentItem().getType().equals(Material.GOLDEN_CARROT))
            {
                healPlayer();
            }
        }
    }


    private void updateFly()
    {
        boolean flyState = player.getAllowFlight();

        player.setAllowFlight(!flyState);
        player.sendMessage(!flyState ? flyOnMessage : flyOffMessage);
        player.openInventory(CommandsInventory.commandsInventory(player));
        SoundEffect.success(player);
    }

    private void updateWeather()
    {
        Bukkit.getServer().dispatchCommand(player, "toggledownfall");
        SoundEffect.success(player);
    }

    private void clearChat()
    {
        for (Player pl : Bukkit.getOnlinePlayers())
        {
            for (int i = 0; i <= 200; i++)
            {
                pl.sendMessage("");
            }
            pl.sendMessage(chatClearedMessage.replace("%player%", player.getName()));
            pl.sendMessage("");
            SoundEffect.success(player);
        }
    }

    private void healPlayer()
    {
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.sendMessage(healMessage);
        SoundEffect.success(player);
    }
}
