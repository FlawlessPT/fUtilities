package pt.flawless.fUtilities.chests;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/** Baú pessoal {@code /bau}. O holder identifica o inventário nos eventos. */
public class PersonalChest implements InventoryHolder {
    public static final int SIZE = 54;

    private final UUID owner;
    private final int number;
    private final Inventory inventory;

    public PersonalChest(UUID owner, int number, ItemStack[] contents) {
        this.owner = owner;
        this.number = number;
        this.inventory = Bukkit.createInventory(this, SIZE, "§8Baú " + number);
        if (contents != null) inventory.setContents(contents);
    }

    public UUID getOwner() {
        return owner;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
