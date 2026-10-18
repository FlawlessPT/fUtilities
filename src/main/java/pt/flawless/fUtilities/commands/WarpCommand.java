package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.menus.WarpMenu;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fUtilities.warps.WarpTeleporter;

/** {@code /warp <lugar>}. Sem argumentos abre o menu. */
public class WarpCommand implements CommandExecutor {
    private final WarpTeleporter teleporter;
    private final WarpStore store;

    public WarpCommand(WarpTeleporter teleporter, WarpStore store) {
        this.store = store;
        this.teleporter = teleporter;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Só jogadores podem usar este comando.");
            return true;
        }
        if (args.length == 0) {
            new WarpMenu(store, player).open();
            return true;
        }
        teleporter.teleport(player, args[0]);
        return true;
    }
}
