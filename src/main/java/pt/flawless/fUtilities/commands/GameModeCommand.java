package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.helpers.GameModeHelper;
import pt.flawless.fapi.sounds.FSound;

public class GameModeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        GameMode mode = args.length == 0 || args.length > 2 ? null : switch (args[0]) {
            case "0" -> GameMode.SURVIVAL;
            case "1" -> GameMode.CREATIVE;
            case "2" -> GameMode.ADVENTURE;
            case "3" -> GameMode.SPECTATOR;
            default -> null;
        };
        if (mode == null) {
            p.sendMessage("§cUso: /gamemode <0|1|2|3> [jogador]");
            FSound.fail(p);
            return true;
        }
        Player target = p;
        if (args.length == 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                p.sendMessage("§cJogador não encontrado.");
                FSound.fail(p);
                return true;
            }
        }
        target.setGameMode(mode);
        String modeLabel = GameModeHelper.getGameModeLabel(mode);
        p.sendMessage(target == p
                ? "§eModo de jogo alterado para §7" + modeLabel + "§e!"
                : "§eModo de jogo de §7" + target.getName() + " §ealterado para §7" + modeLabel + "§e!");
        FSound.success(p);
        return true;
    }
}
