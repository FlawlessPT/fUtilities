package pt.flawless.fUtilities.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pt.flawless.fapi.sounds.FSound;

import java.lang.management.ManagementFactory;
import java.time.Duration;

/** {@code /estatisticas}: TPS, RAM, uptime, jogadores e carga dos mundos. */
public class EstatisticasCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            sender.sendMessage("§cUso: /estatisticas");
            return true;
        }
        Runtime rt = Runtime.getRuntime();
        long usedMb = (rt.totalMemory() - rt.freeMemory()) / 1048576;
        long maxMb = rt.maxMemory() / 1048576;
        Duration up = Duration.ofMillis(ManagementFactory.getRuntimeMXBean().getUptime());
        double tps = Math.min(20.0, Bukkit.getTPS()[0]);

        sender.sendMessage("§eTPS (1 min): §7" + String.format("%.2f", tps));
        sender.sendMessage("§eRAM: §7" + usedMb + " MB / " + maxMb + " MB");
        sender.sendMessage("§eUptime: §7" + up.toDays() + "d " + up.toHoursPart() + "h " + up.toMinutesPart() + "m");
        sender.sendMessage("§eJogadores: §7" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers());
        for (World w : Bukkit.getWorlds()) {
            sender.sendMessage("§e" + w.getName() + ": §7" + w.getEntityCount() + " entidades, "
                    + w.getLoadedChunks().length + " chunks");
        }
        if (sender instanceof org.bukkit.entity.Player p) FSound.success(p);
        return true;
    }
}
