package pt.flawless.FUtilities.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChangeListener implements Listener {
    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setHealth(20);
    }
}
