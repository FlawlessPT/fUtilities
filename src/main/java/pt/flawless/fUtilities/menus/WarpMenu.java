package pt.flawless.fUtilities.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pt.flawless.fUtilities.warps.Warp;
import pt.flawless.fUtilities.warps.WarpMenuSize;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fapi.enums.FInventorySize;
import pt.flawless.fapi.inventory.FInventory;
import pt.flawless.fapi.items.FItemStack;

import java.util.List;

/** Menu {@code /warp}: lê os warps ativos ao construir. O holder identifica o inventário nos eventos. */
public class WarpMenu implements InventoryHolder {
    private final Player player;
    private final List<Warp> shown;
    private final Inventory inventory;

    public WarpMenu(WarpStore store, Player player) {
        this.player = player;
        this.shown = store.all().stream().filter(Warp::active).limit(54).toList();
        int size = WarpMenuSize.forCount(shown.size());
        FInventorySize fsize = FInventorySize.SIZE_9;
        for (FInventorySize s : FInventorySize.values()) if (s.getSize() == size) fsize = s;
        this.inventory = new FInventory(this, fsize, "§8Warps").getInventory();
        for (int i = 0; i < shown.size(); i++) inventory.setItem(i, icon(shown.get(i)));
    }

    private static org.bukkit.inventory.ItemStack icon(Warp w) {
        Material m = Material.matchMaterial(w.material());
        FItemStack item = new FItemStack(m == null ? Material.ENDER_PEARL : m, 1)
                .setDisplayName("§e" + w.name())
                .setDurability(w.data());
        if (w.group() != null && !w.group().isEmpty()) item.addLoreLine("§7Requer: " + w.group());
        for (String line : w.description()) item.addLoreLine(line.replace('&', '§'));
        return item.createItem();
    }

    public void open() {
        player.openInventory(inventory);
    }

    /** Warp mostrado neste slot, ou nulo. */
    public Warp warpAt(int slot) {
        return slot >= 0 && slot < shown.size() ? shown.get(slot) : null;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
