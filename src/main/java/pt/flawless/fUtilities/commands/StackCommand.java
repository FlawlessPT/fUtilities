package pt.flawless.fUtilities.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import pt.flawless.fapi.sounds.FSound;

import java.util.ArrayList;
import java.util.List;

public class StackCommand implements CommandExecutor {
    /** Tamanho máximo da pilha, mesmo para itens que no jogo não empilham. */
    private static final int MAX = 64;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            p.sendMessage("§cUso: /stack");
            return true;
        }
        PlayerInventory inv = p.getInventory();
        ItemStack hand = inv.getItemInMainHand();
        if (hand == null || hand.getType() == Material.AIR) {
            p.sendMessage("§cNão tens nenhum item na mão.");
            FSound.fail(p);
            return true;
        }
        // slots do inventário principal (0-35) com o mesmo item; o da mão vem primeiro
        List<Integer> slots = new ArrayList<>();
        slots.add(inv.getHeldItemSlot());
        int total = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack item = inv.getItem(i);
            if (item == null || !item.isSimilar(hand)) continue;
            total += item.getAmount();
            if (i != inv.getHeldItemSlot()) slots.add(i);
        }
        for (int slot : slots) inv.setItem(slot, null);
        int left = total;
        for (int slot : slots) {
            if (left <= 0) break;
            ItemStack stack = hand.clone();
            stack.setAmount(Math.min(MAX, left));
            inv.setItem(slot, stack);
            left -= stack.getAmount();
        }
        p.updateInventory();
        p.sendMessage("§aItens juntados: §7" + total + "§a.");
        FSound.success(p);
        return true;
    }
}
