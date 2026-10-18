package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.warps.WarpTeleporter;

/** {@code /warp <lugar>}. O menu (sem argumentos) vem na Task 4. */
public class WarpCommand implements CommandExecutor {
    private final WarpTeleporter teleporter;

    public WarpCommand(WarpTeleporter teleporter) {
        this.teleporter = teleporter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Só jogadores podem usar este comando.");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("§cUso: /warp <lugar>");
            return true;
        }
        teleporter.teleport(player, args[0]);
        return true;
    }
}
