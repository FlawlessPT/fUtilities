package pt.flawless.FUtilities.inventories;

import mcmacau.barberianscore.Inventory.BInventory;
import mcmacau.barberianscore.ItemStack.BItemStack;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pt.flawless.FUtilities.managers.VariablesManager;

public class CommandsMenu extends VariablesManager
{
    public static void load(Player p)
    {
        BInventory bInventory = new BInventory(BInventory.BInventorySize.SIZE_36, titulo);
        BItemStack fly = new BItemStack(Material.FEATHER, 1).setDisplaName(nameFly).addLoreLine(loreFly);
        BItemStack flyState;
        BItemStack rain = new BItemStack(Material.WATER_BUCKET, 1).setDisplaName(nameRain).addLoreLine(loreRain);
        BItemStack clearChat = new BItemStack(Material.PAPER, 1).setDisplaName(nameChat).addLoreLine(loreChat);
        BItemStack heal = new BItemStack(Material.GOLDEN_CARROT, 1).setDisplaName(nameHeal).addLoreLine(loreHeal);

        BItemStack filler = new BItemStack(Material.STAINED_GLASS_PANE, 1).setDisplaName(nameFiller);
        filler.getItem().setDurability(DyeColor.BLACK.getData());

        if (p.getAllowFlight())
        {
            flyState = new BItemStack(Material.STAINED_GLASS_PANE, 1).setDisplaName(nameFlyStateOn);
            flyState.getItem().setDurability(DyeColor.YELLOW.getData());
        }
        else
        {
            flyState = new BItemStack(Material.STAINED_GLASS_PANE, 1).setDisplaName(nameFlyStateOff);
            flyState.getItem().setDurability(DyeColor.RED.getData());
        }


        bInventory.setItem(10, fly);
        bInventory.setItem(19, flyState);
        bInventory.setItem(11, rain);
        bInventory.setItem(20, filler);
        bInventory.setItem(12, clearChat);
        bInventory.setItem(21, filler);
        bInventory.setItem(13, heal);
        bInventory.setItem(22, filler);

        p.openInventory(bInventory.getInventory());
    }
}
