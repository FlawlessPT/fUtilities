package pt.flawless.fUtilities.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pt.flawless.fapi.items.FItemStack;

/** Menu {@code /cores}: 27 slots, sem vidro, só informativo (os cliques são cancelados pelo listener). */
public class ColorsMenu implements InventoryHolder {
    private final Inventory inventory = Bukkit.createInventory(this, 27, "§8Cores e formatos");

    public ColorsMenu() {
        inventory.setItem(11, new FItemStack(Material.WHITE_DYE, 1).setDisplayName("§eCores")
                .addLoreLine("§7Usa o código no chat, em placas e nomes.")
                .addLoreLine("§0&0 §1&1 §2&2 §3&3 §4&4 §5&5 §6&6 §7&7")
                .addLoreLine("§8&8 §9&9 §a&a §b&b §c&c §d&d §e&e §f&f")
                .addLoreLine("§7Exemplo: §f&aOlá §7fica §aOlá")
                .createItem());
        inventory.setItem(15, new FItemStack(Material.FEATHER, 1).setDisplayName("§eFormatos")
                .addLoreLine("§7Junta ao código de uma cor.")
                .addLoreLine("§f§l&l negrito §f§o&o itálico §f§n&n sublinhado")
                .addLoreLine("§f§m&m riscado §f§k&k §f&k ofuscado §f&r repor")
                .addLoreLine("§7Exemplo: §f&c&lOlá §7fica §c§lOlá")
                .createItem());
    }

    public void open(Player p) {
        p.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
