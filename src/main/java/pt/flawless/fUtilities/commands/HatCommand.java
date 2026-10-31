package pt.flawless.fUtilities.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pt.flawless.fapi.sounds.FSound;

/** {@code /hat}: troca o item da mão com o capacete. */
public class HatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage("§cUso: /hat");
            return true;
        }
        PlayerInventory inv = p.getInventory();
        ItemStack hand = inv.getItemInMainHand();
        if (hand.getType() == Material.AIR) {
            p.sendMessage("§cTens de ter um item na mão.");
            FSound.fail(p);
            return true;
        }
        ItemStack old = inv.getHelmet();
        inv.setHelmet(hand);
        inv.setItemInMainHand(old);
        p.sendMessage("§eColocaste o item na cabeça.");
        FSound.success(p);
        return true;
    }
}
