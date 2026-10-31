package pt.flawless.fUtilities.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pt.flawless.fUtilities.commands.VanishCommand;
import pt.flawless.fapi.items.FItemStack;

import java.util.ArrayList;
import java.util.List;

/** Menu {@code /lista}: 54 slots, moldura de vidro, jogadores online nos 28 slots interiores, setas de página. */
public class PlayerListMenu implements InventoryHolder {
    private static final int SIZE = 54;
    private static final int PER_PAGE = 28;
    private static final int PREV_SLOT = 45;
    private static final int NEXT_SLOT = 53;

    private final Player viewer;
    private final int page;
    private final boolean hasPrev;
    private final boolean hasNext;
    private final Inventory inventory = Bukkit.createInventory(this, SIZE, "§8Jogadores online");

    public PlayerListMenu(Player viewer, int page, VanishCommand vanish) {
        this.viewer = viewer;
        boolean seesAll = viewer.hasPermission("futilities.vanish.see");
        List<Player> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (seesAll || !vanish.isVanished(p.getUniqueId())) players.add(p);
        }
        int pages = Math.max(1, (players.size() + PER_PAGE - 1) / PER_PAGE);
        this.page = Math.max(0, Math.min(page, pages - 1));
        this.hasPrev = this.page > 0;
        this.hasNext = this.page < pages - 1;

        ItemStack glass = new FItemStack(Material.GRAY_STAINED_GLASS_PANE, 1, " ").createItem();
        for (int i = 0; i < SIZE; i++) {
            int row = i / 9, col = i % 9;
            if (row == 0 || row == 5 || col == 0 || col == 8) inventory.setItem(i, glass);
        }
        int from = this.page * PER_PAGE;
        int to = Math.min(players.size(), from + PER_PAGE);
        for (int i = from; i < to; i++) {
            int n = i - from;
            inventory.setItem((n / 7 + 1) * 9 + n % 7 + 1, head(players.get(i)));
        }
        if (hasPrev) inventory.setItem(PREV_SLOT, arrow("§ePágina anterior", "§7Clica para ir à página " + this.page));
        if (hasNext) inventory.setItem(NEXT_SLOT, arrow("§ePágina seguinte", "§7Clica para ir à página " + (this.page + 2)));
    }

    @SuppressWarnings("deprecation")
    private static ItemStack head(Player p) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(p);
        meta.setDisplayName("§e" + p.getName());
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack arrow(String name, String lore) {
        return new FItemStack(Material.ARROW, 1).setDisplayName(name).addLoreLine(lore).createItem();
    }

    public void open() {
        viewer.openInventory(inventory);
    }

    public int page() {
        return page;
    }

    public boolean isPrev(int slot) {
        return hasPrev && slot == PREV_SLOT;
    }

    public boolean isNext(int slot) {
        return hasNext && slot == NEXT_SLOT;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
