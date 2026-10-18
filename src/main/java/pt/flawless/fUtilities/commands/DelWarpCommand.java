package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.warps.WarpStore;
import pt.flawless.fapi.messages.Messages;
import pt.flawless.fapi.sounds.FSound;

/** {@code /delwarp <lugar>}. */
public class DelWarpCommand implements CommandExecutor {
    private final WarpStore store;

    public DelWarpCommand(WarpStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("futilities.warp.admin")) {
            Messages.noPermission(sender);
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("§cUso: /delwarp <lugar>");
            return true;
        }
        boolean ok;
        try {
            ok = store.delete(args[0]);
        } catch (RuntimeException e) {
            sender.sendMessage("§cErro ao apagar o warp: " + e.getMessage());
            return true;
        }
        sender.sendMessage(ok ? "§aWarp " + WarpStore.normalize(args[0]) + " apagado." : "§cEsse warp não existe.");
        if (sender instanceof Player p) {
            if (ok) FSound.success(p);
            else FSound.fail(p);
        }
        return true;
    }
}
