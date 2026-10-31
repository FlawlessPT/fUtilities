package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pt.flawless.fUtilities.helpers.SkinLookup;
import pt.flawless.fapi.sounds.FSound;

import java.util.Map;

/** {@code /head <cabeça> [jogador]}: dá a cabeça de um jogador a ti ou a um jogador online. */
public class HeadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("§cUso: /head <cabeça> [jogador]");
            return true;
        }
        Player target;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("§cJogador não encontrado.");
                if (sender instanceof Player p) FSound.fail(p);
                return true;
            }
        } else if (sender instanceof Player p) {
            target = p;
        } else {
            sender.sendMessage("§cUso: /head <cabeça> <jogador>");
            return true;
        }
        String name = args[0];
        Plugin plugin = JavaPlugin.getProvidingPlugin(HeadCommand.class);
        // A skin é pedida à Mojang fora da thread principal; o item só se dá quando o perfil chega.
        SkinLookup.lookup(name).thenAccept(profile ->
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (profile == null) {
                        sender.sendMessage("§cJogador não encontrado.");
                        if (sender instanceof Player p) FSound.fail(p);
                        return;
                    }
                    ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta meta = (SkullMeta) head.getItemMeta();
                    meta.setPlayerProfile(profile);
                    head.setItemMeta(meta);
                    Map<Integer, ItemStack> left = target.getInventory().addItem(head);
                    left.values().forEach(i -> target.getWorld().dropItem(target.getLocation(), i));
                    sender.sendMessage("§eDeste a cabeça de §7" + profile.getName() + " §ea §7" + target.getName() + "§e.");
                    if (!target.equals(sender)) target.sendMessage("§eRecebeste a cabeça de §7" + profile.getName() + "§e.");
                    if (sender instanceof Player p) FSound.success(p);
                }));
        return true;
    }
}
