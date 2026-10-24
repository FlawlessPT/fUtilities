package pt.flawless.fUtilities.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pt.flawless.fUtilities.FUtilities;
import pt.flawless.fUtilities.arenas.Arena;
import pt.flawless.fUtilities.arenas.ArenaStore;
import pt.flawless.fapi.items.FItemStack;

import java.util.List;

/** Menu {@code /arenas}: 5 linhas, espada informativa no meio da 2.ª, arenas na 4.ª a partir do 2.º slot. */
public class ArenaMenu implements InventoryHolder {
    private static final int SIZE = 45;
    private static final int INFO_SLOT = 13;
    private static final int FIRST_SLOT = 28;
    private static final int LAST_SLOT = 35;

    private final Player player;
    private final List<Arena> shown;
    private final Inventory inventory;

    public ArenaMenu(ArenaStore store, Player player) {
        this.player = player;
        this.shown = store.all().stream().limit(LAST_SLOT - FIRST_SLOT + 1).toList();
        FileConfiguration cfg = FUtilities.getMainPlugin().getConfig();
        String title = cfg.getString("Menus.Arenas.Titulo", "&8Arenas").replace('&', '§');
        this.inventory = Bukkit.createInventory(this, SIZE, title);
        FItemStack info = new FItemStack(Material.DIAMOND_SWORD, 1)
                .setDisplayName(cfg.getString("Menus.Arenas.Info.Name", "&cArenas").replace('&', '§'));
        for (String line : cfg.getStringList("Menus.Arenas.Info.Lore")) info.addLoreLine(line.replace('&', '§'));
        inventory.setItem(INFO_SLOT, info.createItem());
        for (int i = 0; i < shown.size(); i++) inventory.setItem(FIRST_SLOT + i, icon(shown.get(i)));
    }

    private static ItemStack icon(Arena a) {
        FItemStack item = new FItemStack(Material.getMaterial(a.item()), 1).setDisplayName(a.nome().replace('&', '§'));
        for (String line : a.lore()) item.addLoreLine(line.replace('&', '§'));
        return item.createItem();
    }

    public void open() {
        player.openInventory(inventory);
    }

    /** Arena mostrada neste slot, ou nula. */
    public Arena arenaAt(int slot) {
        int i = slot - FIRST_SLOT;
        return i >= 0 && i < shown.size() ? shown.get(i) : null;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
