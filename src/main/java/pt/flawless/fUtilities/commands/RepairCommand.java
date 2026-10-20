package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pt.flawless.fapi.sounds.FSound;

public class RepairCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length == 0) {
            if (!repair(p.getItemInHand())) {
                p.sendMessage("§cO item na tua mão não pode ser reparado.");
                FSound.fail(p);
                return true;
            }
            p.sendMessage("§aItem reparado.");
            FSound.success(p);
        } else if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            int count = 0;
            for (ItemStack item : p.getInventory().getContents()) if (repair(item)) count++;
            for (ItemStack item : p.getInventory().getArmorContents()) if (repair(item)) count++;
            if (count == 0) {
                p.sendMessage("§cNão tens itens para reparar.");
                FSound.fail(p);
                return true;
            }
            p.sendMessage("§aReparados §7" + count + "§a itens.");
            FSound.success(p);
        } else {
            p.sendMessage("§cUso: /repair [all]");
            return true;
        }
        p.updateInventory();
        return true;
    }

    /** Repõe a durabilidade; false se o item não tem durabilidade ou já está inteiro. */
    private static boolean repair(ItemStack item) {
        if (item == null || item.getType().getMaxDurability() <= 0 || item.getDurability() == 0) return false;
        item.setDurability((short) 0);
        return true;
    }
}
