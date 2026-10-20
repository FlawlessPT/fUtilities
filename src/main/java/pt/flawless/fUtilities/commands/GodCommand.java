package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.listeners.GodListener;
import pt.flawless.fapi.sounds.FSound;

public class GodCommand implements CommandExecutor {
    private final GodListener god;

    public GodCommand(GodListener god) {
        this.god = god;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 0) {
            p.sendMessage("§cUso: /god");
            return true;
        }
        p.sendMessage(god.toggle(p) ? "§aModo deus ativado." : "§cModo deus desativado.");
        FSound.success(p);
        return true;
    }
}
