package pt.flawless.fUtilities.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pt.flawless.fUtilities.warps.ReplaceConfirmations;
import pt.flawless.fUtilities.warps.Warp;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fapi.messages.Messages;
import pt.flawless.fapi.sounds.FSound;

import java.util.List;

/** {@code /setwarp <lugar> [grupo]}. */
public class SetWarpCommand implements CommandExecutor {
    private final WarpStore store;
    private final ReplaceConfirmations confirmations;

    public SetWarpCommand(WarpStore store, ReplaceConfirmations confirmations) {
        this.store = store;
        this.confirmations = confirmations;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Só jogadores podem usar este comando.");
            return true;
        }
        if (!player.hasPermission("futilities.warp.admin")) {
            Messages.noPermission(player);
            return true;
        }
        if (args.length == 0 || args.length > 2 || args[0].trim().isEmpty()) {
            player.sendMessage("§cUso: /setwarp <lugar> [grupo]");
            FSound.fail(player);
            return true;
        }
        String name = args[0].trim();
        if (name.contains(".")) { // o ponto é separador de caminho no YAML
            player.sendMessage("§cO nome do warp não pode conter '.'.");
            FSound.fail(player);
            return true;
        }
        ItemStack hand = player.getItemInHand();
        boolean air = hand == null || hand.getType() == Material.AIR;
        Warp old = store.get(name).orElse(null);
        Location l = player.getLocation();
        Warp warp = new Warp(name, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch(),
                air ? "ENDER_PEARL" : hand.getType().name(), air || hand.getType().getMaxDurability() > 0 ? 0 : hand.getDurability(),
                old == null || old.active(), args.length > 1 ? args[1] : null,
                old == null ? List.of() : old.description(),
                old == null ? List.of() : old.aliases(),
                old == null ? null : old.title());
        if (old != null) {
            confirmations.request(player.getUniqueId(), warp);
            player.sendMessage("§eO warp " + old.name() + " já existe. Escreve 'sim' para o substituir.");
            return true;
        }
        try {
            store.save(warp);
        } catch (RuntimeException e) {
            player.sendMessage("§cErro ao guardar o warp: " + e.getMessage());
            FSound.fail(player);
            return true;
        }
        player.sendMessage("§aWarp " + name + " definido.");
        FSound.success(player);
        return true;
    }
}
