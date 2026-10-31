package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** {@code /online}: número de jogadores online (vanished só contam para quem os vê). */
public class OnlineCommand implements CommandExecutor {
    private final VanishCommand vanish;

    public OnlineCommand(VanishCommand vanish) {
        this.vanish = vanish;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage("§cUso: /online");
            return true;
        }
        boolean seesAll = sender.hasPermission("futilities.vanish.see");
        long count = Bukkit.getOnlinePlayers().stream()
                .filter(p -> seesAll || !vanish.isVanished(p.getUniqueId()))
                .count();
        sender.sendMessage("§eJogadores online: §7" + count);
        return true;
    }
}
