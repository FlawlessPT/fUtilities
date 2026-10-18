package pt.flawless.fUtilities.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import pt.flawless.fapi.chat.Channel;
import pt.flawless.fapi.chat.ChatApi;
import pt.flawless.fapi.enums.FInventorySize;
import pt.flawless.fapi.inventory.FInventory;
import pt.flawless.fapi.items.FItemStack;

/** Menu {@code /prefs}. O holder identifica o inventário nos eventos. */
public class PrefsMenu implements InventoryHolder {
    public static final int CHAT_SLOT = 13;

    private final ChatApi chat;
    private final Player player;
    private final Inventory inventory;

    public PrefsMenu(ChatApi chat, Player player) {
        this.chat = chat;
        this.player = player;
        this.inventory = new FInventory(this, FInventorySize.SIZE_27, "§8Preferências").getInventory();
    }

    /** Desenha o estado atual; chamar também depois de uma alteração. */
    public void render() {
        Channel current = chat.getDefaultChannel(player.getUniqueId());
        String name = current == Channel.GLOBAL ? "§aGlobal" : "§aLocal";
        String next = current == Channel.GLOBAL ? "Local" : "Global";
        inventory.setItem(CHAT_SLOT, new FItemStack(Material.BOOK_AND_QUILL, 1)
                .setDisplayName("§eChat padrão: " + name)
                .addLoreLine("§7Canal do chat normal.")
                .addLoreLine("§7Clica para mudar para " + next + ".")
                .createItem());
    }

    public void open() {
        render();
        player.openInventory(inventory);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public ChatApi getChat() {
        return chat;
    }
}
