package pt.flawless.fUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pt.flawless.fUtilities.helpers.GameModeHelper;
import pt.flawless.fUtilities.managers.MessagesManager;
import pt.flawless.fUtilities.managers.VariablesManager;
import pt.flawless.fUtilities.menus.CommandsMenu;
import pt.flawless.fapi.sounds.FSound;

public class CommandsInventoryClickListener extends VariablesManager implements Listener {
    @EventHandler
    public void onCommandsMenuInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase(titulo)) {
            e.setCancelled(true);

            Material clickedItem = e.getCurrentItem().getType();

            if (clickedItem.equals(Material.AIR)) {
                e.setCancelled(true);
                return;
            }

            if (clickedItem.equals(Material.FEATHER)) {
                checkFly(e);
            }

            if (clickedItem.equals(Material.WATER_BUCKET)) {
                changeRain(e);
            }

            if (clickedItem.equals(Material.PAPER)) {
                clearChat(e);
            }

            if (clickedItem.equals(Material.GOLDEN_CARROT)) {
                healPlayer(e);
            }

            if (clickedItem.equals(Material.TORCH)) {
                setDayTime(e);
            }

            if (clickedItem.equals(Material.COAL)) {
                setNightTime(e);
            }

            if (clickedItem.equals(Material.STONE_PICKAXE) || clickedItem.equals(Material.GRASS) || clickedItem.equals(Material.COMPASS) || clickedItem.equals(Material.ENDER_PEARL)) {
//                switch (clickedItem) {
//                    case STONE_PICKAXE ->
//                            e.setCurrentItem(new FItemStack(Material.GRASS, 1).setDisplayName("§2Creative").createItem());
//                    case GRASS ->
//                            e.setCurrentItem(new FItemStack(Material.COMPASS, 1).setDisplayName("§8Adventure").createItem());
//                    case COMPASS ->
//                            e.setCurrentItem(new FItemStack(Material.ENDER_PEARL, 1).setDisplayName("§9Spectator").createItem());
//                    case ENDER_PEARL ->
//                            e.setCurrentItem(new FItemStack(Material.STONE_PICKAXE, 1).setDisplayName("§4Survival").createItem());
//                }

                setGameMode(e);
            }
        }
    }


    private void checkFly(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        boolean flyState = player.getAllowFlight();

        if (flyState) {
            player.setAllowFlight(false);
            player.sendMessage(flyOffMessage);
        } else {
            player.setAllowFlight(true);
            player.sendMessage(flyOnMessage);
        }

        new CommandsMenu().loadMenu(player);
        FSound.success(player);
    }

    private void changeRain(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        boolean prevRainState = player.getWorld().hasStorm();

        player.getWorld().setStorm(!prevRainState);

        new CommandsMenu().loadMenu(player);

        player.sendMessage("§eChuva §7%rain_state%§e.".replace("%rain_state%", prevRainState ? "Desativada" : "Ativada"));
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

        }

        FSound.success(p);
    }

    private void healPlayer(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        player.setHealth(20.0);
        player.setFoodLevel(20);

        player.sendMessage(healMessage);
        FSound.success(player);
    }

    private void setDayTime(InventoryClickEvent e) {
        World world = e.getWhoClicked().getWorld();
        Player player = (Player) e.getWhoClicked();

        // TODO: Reuse same logic as command
        world.setStorm(false);
        world.setTime(1000);
        world.setFullTime(1000);

        player.sendMessage("§eTempo definido para §2Dia§e.");
        FSound.success(player);
    }

    private void setNightTime(InventoryClickEvent e) {
        World world = e.getWhoClicked().getWorld();
        Player player = (Player) e.getWhoClicked();

        // TODO: Reuse same logic as command
        world.setStorm(false);
        world.setTime(16000);
        world.setFullTime(16000);

        player.sendMessage("§eTempo definido para §5Noite§e.");
        FSound.success(player);
    }

    private void setGameMode(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        switch (player.getGameMode()) {
            case SURVIVAL -> player.setGameMode(GameMode.CREATIVE);
            case CREATIVE -> player.setGameMode(GameMode.ADVENTURE);
            case ADVENTURE -> player.setGameMode(GameMode.SPECTATOR);
            case SPECTATOR -> player.setGameMode(GameMode.SURVIVAL);
        }

        new CommandsMenu().loadMenu(player);

        player.sendMessage(MessagesManager.gamemode_change.replace("%gamemode%", GameModeHelper.getGameModeLabel(player.getGameMode())));
        FSound.success(player);
    }
}
