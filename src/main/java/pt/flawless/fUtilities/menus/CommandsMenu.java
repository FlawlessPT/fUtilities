package pt.flawless.fUtilities.menus;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.FUtilities;
import pt.flawless.fapi.enums.FInventorySize;
import pt.flawless.fapi.inventory.FInventory;
import pt.flawless.fapi.items.FItemStack;
import pt.flawless.fapi.menus.IBaseMenu;

public class CommandsMenu implements IBaseMenu {
    /** Texto do config em Menus.Comandos.(path), com & convertido em §. */
    public static String text(String path) {
        return FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos." + path, "").replace("&", "§");
    }

    @Override
    public void loadMenu(Player player) {
        String nameFlyStateOn = text("Itens.FlyState.Name_On");
        String nameFlyStateOff = text("Itens.FlyState.Name_Off");
        FInventory fInventory = new FInventory(FInventorySize.SIZE_36, text("Titulo"));
        FItemStack fly = new FItemStack(Material.FEATHER, 1).setDisplayName(text("Itens.Fly.Name")).addLoreLine(text("Itens.Fly.Lore"));
        FItemStack flyState = new FItemStack(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOff);
        FItemStack rain = new FItemStack(Material.WATER_BUCKET, 1).setDisplayName(text("Itens.Rain.Name")).addLoreLine(text("Itens.Rain.Lore"));
        FItemStack rainState = new FItemStack(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOff);
        FItemStack clearChat = new FItemStack(Material.PAPER, 1).setDisplayName(text("Itens.Chat.Name")).addLoreLine(text("Itens.Chat.Lore"));
        FItemStack heal = new FItemStack(Material.GOLDEN_CARROT, 1).setDisplayName(text("Itens.Heal.Name")).addLoreLine(text("Itens.Heal.Lore"));
        FItemStack day = new FItemStack(Material.TORCH, 1).setDisplayName("§2Dia").addLoreLine("§7Clica para colocar de dia.");
        FItemStack night = new FItemStack(Material.COAL, 1).setDisplayName("§5Noite").addLoreLine("§7Clica para colocar de noite.");
        FItemStack gameMode;

        FItemStack filler = new FItemStack(Material.BLACK_STAINED_GLASS_PANE, 1).setDisplayName(text("Itens.Filler.Name"));

        if (player.getWorld().hasStorm()) {
            rainState = new FItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOn);
        } else {
            rainState = new FItemStack(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOff);
        }

        if (player.getAllowFlight()) {
            flyState = new FItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOn);
        } else {
            flyState = new FItemStack(Material.RED_STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOff);
        }

        switch (player.getGameMode()) {
            case CREATIVE -> gameMode = new FItemStack(Material.GRASS_BLOCK, 1).setDisplayName("§2Creative");
            case ADVENTURE -> gameMode = new FItemStack(Material.COMPASS, 1).setDisplayName("§8Adventure");
            case SPECTATOR -> gameMode = new FItemStack(Material.ENDER_PEARL, 1).setDisplayName("§9Spectator");
            default -> gameMode = new FItemStack(Material.STONE_PICKAXE, 1).setDisplayName("§4Survival"); // survival
        }

        fInventory.setItem(10, fly);
        fInventory.setItem(19, flyState);
        fInventory.setItem(11, rain);
        fInventory.setItem(20, rainState);
        fInventory.setItem(12, clearChat);
        fInventory.setItem(21, filler);
        fInventory.setItem(13, heal);
        fInventory.setItem(22, filler);
        fInventory.setItem(14, day);
        fInventory.setItem(23, filler);
        fInventory.setItem(15, night);
        fInventory.setItem(24, filler);
        fInventory.setItem(16, gameMode);
        fInventory.setItem(25, filler);

        player.openInventory(fInventory.getInventory());
    }
}
