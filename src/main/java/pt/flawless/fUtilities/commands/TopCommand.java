package pt.flawless.fUtilities.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

/** {@code /top}: teleporta para o bloco mais alto da tua coluna. */
public class TopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage("§cUso: /top");
            return true;
        }
        Location loc = p.getLocation();
        int y = loc.getWorld().getHighestBlockYAt(loc) + 1;
        Location dest = new Location(loc.getWorld(), loc.getBlockX() + 0.5, y, loc.getBlockZ() + 0.5, loc.getYaw(), loc.getPitch());
        p.teleport(dest);
        p.sendMessage("§eTeleportado para o topo.");
        FSound.success(p);
        return true;
    }
}
