package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.menus.PlayerListMenu;

/** {@code /lista}: menu com os jogadores online. */
public class ListaCommand implements CommandExecutor {
    private final VanishCommand vanish;

    public ListaCommand(VanishCommand vanish) {
        this.vanish = vanish;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cApenas jogadores podem usar este comando.");
            return true;
        }
        new PlayerListMenu(p, 0, vanish).open();
        return true;
    }
}
