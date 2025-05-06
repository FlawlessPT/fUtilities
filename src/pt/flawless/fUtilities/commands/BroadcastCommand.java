package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;
import pt.flawless.fapi.title.FTitle;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = (Player) commandSender;

        if (args.length == 0) {
            p.sendMessage("§cArgumentos em falta: /broadcast [mensagem]");
            FSound.fail(p);

            return false;
        }

        FTitle titleAlert = new FTitle(append(args));

        for (Player player : Bukkit.getOnlinePlayers()) {
            titleAlert.setPlayer(player);
            titleAlert.send();
        }

        return false;
    }

    private String append(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }

        return stringBuilder.toString();
    }
}
