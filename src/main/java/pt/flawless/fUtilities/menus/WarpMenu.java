package pt.flawless.fUtilities.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pt.flawless.fUtilities.warps.Warp;
import pt.flawless.fUtilities.warps.WarpMenuLayout;
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
        this.shown = store.all().stream().filter(Warp::active).limit(WarpMenuLayout.capacity()).toList();
        this.inventory = new FInventory(this, FInventorySize.SIZE_54, "§8Warps").getInventory();
        // nome " " (espaço): o Bukkit 1.8 aceita e o tooltip fica vazio
        org.bukkit.inventory.ItemStack glass = new FItemStack(Material.STAINED_GLASS_PANE, 1)
                .setDisplayName(" ").setDurability((short) 7).createItem();
        for (int i = 0; i < WarpMenuLayout.SIZE; i++) if (WarpMenuLayout.isBorder(i)) inventory.setItem(i, glass);
        List<Integer> slots = WarpMenuLayout.innerSlots();
        for (int i = 0; i < shown.size(); i++) inventory.setItem(slots.get(i), icon(shown.get(i)));
    }

    private static org.bukkit.inventory.ItemStack icon(Warp w) {
        Material m = Material.matchMaterial(w.material());
        FItemStack item = new FItemStack(m == null ? Material.ENDER_PEARL : m, 1)
                .setDisplayName(w.title() != null ? w.title().replace('&', '§') : "§e" + w.name())
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
        int i = WarpMenuLayout.innerSlots().indexOf(slot);
        return i >= 0 && i < shown.size() ? shown.get(i) : null;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
