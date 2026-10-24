package pt.flawless.fUtilities.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pt.flawless.fapi.sounds.FSound;

import java.util.Map;

public class ItemInfoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            p.sendMessage("§cUso: /iteminfo");
            return true;
        }
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || item.getType() == Material.AIR) {
            p.sendMessage("§cNão tens nenhum item na mão.");
            FSound.fail(p);
            return true;
        }
        Material type = item.getType();
        p.sendMessage("§8§m                    ");
        p.sendMessage("§eMaterial: §7" + type.name());
        p.sendMessage("§eQuantidade: §7" + item.getAmount() + "§e Máx. pilha: §7" + type.getMaxStackSize());
        if (type.getMaxDurability() > 0) {
            p.sendMessage("§eDurabilidade: §7" + (type.getMaxDurability() - (item.getItemMeta() instanceof org.bukkit.inventory.meta.Damageable d ? d.getDamage() : 0)) + "/" + type.getMaxDurability());
        }
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) p.sendMessage("§eNome: §r" + meta.getDisplayName());
        if (meta != null && meta.hasLore()) {
            p.sendMessage("§eLore:");
            for (String line : meta.getLore()) p.sendMessage("§7 - §r" + line);
        }
        Map<Enchantment, Integer> enchants = item.getEnchantments();
        if (!enchants.isEmpty()) {
            p.sendMessage("§eEncantamentos:");
            enchants.forEach((e, level) -> p.sendMessage("§7 - " + e.getName() + " " + level));
        }
        p.sendMessage("§8§m                    ");
        return true;
    }
}
