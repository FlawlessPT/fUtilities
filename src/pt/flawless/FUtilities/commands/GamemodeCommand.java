package pt.flawless.FUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import pt.flawless.FUtilities.api.Som;
import pt.flawless.FUtilities.managers.MessagesManager;

public class GamemodeCommand implements CommandExecutor, Listener
{

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        Player p = (Player) sender;

        if (args.length == 0)
        {

            p.sendMessage("§cArgumentos em falta: /gamemode [0/1/2/3] [jogador]");
            Som.fail(p);

            return false;
        }

        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("0"))
            {

                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(MessagesManager.gm0);
                Som.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("1"))
            {

                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(MessagesManager.gm1);
                Som.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("2"))
            {

                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(MessagesManager.gm2);
                Som.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("3"))
            {

                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(MessagesManager.gm3);
                Som.success(p);

                return false;
            }
        }

        if (args.length == 2)
        {

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null)
            {

                p.sendMessage(MessagesManager.jogadorOff);
                Som.fail(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("0"))
            {

                target.setGameMode(GameMode.SURVIVAL);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + " §epara §7Sobrevivência§e!");
                Som.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("1"))
            {

                target.setGameMode(GameMode.CREATIVE);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + " §epara §7Criativo§e!");
                Som.success(p);

                return false;
            }
            if (args[0].equalsIgnoreCase("2"))
            {

                target.setGameMode(GameMode.ADVENTURE);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + "§epara §7Aventura§e!");
                Som.success(p);

                return false;
            }

            if (args[0].equalsIgnoreCase("3"))
            {

                target.setGameMode(GameMode.SPECTATOR);
                p.sendMessage("§eAlteraste o modo de jogo de §7" + target.getName() + " §epara §7Espectador§e!");
                Som.success(p);

                return false;
            }
        }

        return false;
    }

    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e)
    {
        e.getPlayer().setFoodLevel(20);
        e.getPlayer().setHealth(20);
    }
}
