package pt.flawless.fUtilities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pt.flawless.fUtilities.commands.LixoCommand;

/** Apaga o conteúdo do inventário do {@code /lixo} ao fechar. */
public class LixoListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof LixoCommand.LixoHolder) e.getInventory().clear();
    }
}
