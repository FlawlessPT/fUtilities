package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/** {@code /lixo}: abre um inventário onde os itens deixados são apagados ao fechar (ver LixoListener). */
public class LixoCommand implements CommandExecutor {
    /** Holder para o listener reconhecer o inventário. */
    public static class LixoHolder implements InventoryHolder {
        private Inventory inventory;

        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage("§cUso: /lixo");
            return true;
        }
        LixoHolder holder = new LixoHolder();
        holder.inventory = Bukkit.createInventory(holder, 54, "§8Lixo");
        p.openInventory(holder.inventory);
        return true;
    }
}
