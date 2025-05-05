package pt.flawless.fUtilities.menus;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.managers.VariablesManager;
import pt.flawless.fapi.enums.FInventorySize;
import pt.flawless.fapi.inventory.FInventory;
import pt.flawless.fapi.items.FItemStack;
import pt.flawless.fapi.menus.IBaseMenu;

public class CommandsMenu extends VariablesManager implements IBaseMenu
{
    @Override
    public void loadMenu(Player player) {
        FInventory fInventory = new FInventory(FInventorySize.SIZE_36, titulo);
        FItemStack fly = new FItemStack(Material.FEATHER, 1).setDisplayName(nameFly).addLoreLine(loreFly);
        FItemStack flyState = new FItemStack(Material.STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOff);
        FItemStack rain = new FItemStack(Material.WATER_BUCKET, 1).setDisplayName(nameRain).addLoreLine(loreRain);
        FItemStack clearChat = new FItemStack(Material.PAPER, 1).setDisplayName(nameChat).addLoreLine(loreChat);
        FItemStack heal = new FItemStack(Material.GOLDEN_CARROT, 1).setDisplayName(nameHeal).addLoreLine(loreHeal);

        FItemStack filler = new FItemStack(Material.STAINED_GLASS_PANE, 1).setDisplayName(nameFiller).setDurability(DyeColor.BLACK.getData());

        if (player.getAllowFlight())
        {
            flyState = new FItemStack(Material.STAINED_GLASS_PANE, 1).setDisplayName(nameFlyStateOn).setDurability(DyeColor.YELLOW.getData());
        }
        else
        {
            flyState.setDisplayName(nameFlyStateOff).setDurability(DyeColor.RED.getData());
        }

        fInventory.setItem(10, fly);
        fInventory.setItem(19, flyState);
        fInventory.setItem(11, rain);
        fInventory.setItem(20, filler);
        fInventory.setItem(12, clearChat);
        fInventory.setItem(21, filler);
        fInventory.setItem(13, heal);
        fInventory.setItem(22, filler);

        player.openInventory(fInventory.getInventory());
    }
}
