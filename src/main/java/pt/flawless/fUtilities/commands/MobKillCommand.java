package pt.flawless.fUtilities.commands;

import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** {@code /mobkill <tipo|all> [raio]}: mata mobs à tua volta (nunca jogadores). */
public class MobKillCommand implements TabExecutor {
    private static final int DEFAULT_RADIUS = 25;
    private static final int MAX_RADIUS = 200;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length < 1 || args.length > 2) {
            p.sendMessage("§cUso: /mobkill <tipo|all> [raio]");
            return true;
        }
        int radius = DEFAULT_RADIUS;
        if (args.length == 2) {
            try {
                radius = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                radius = 0;
            }
            if (radius < 1 || radius > MAX_RADIUS) {
                p.sendMessage("§cUso: /mobkill <tipo|all> [raio] §7(raio de 1 a " + MAX_RADIUS + ")");
                return true;
            }
        }
        EntityType type = null;
        if (!args[0].equalsIgnoreCase("all")) {
            NamespacedKey key = NamespacedKey.fromString(args[0].toLowerCase(Locale.ROOT));
            type = key == null ? null : Registry.ENTITY_TYPE.get(key);
            if (type == null) {
                p.sendMessage("§cTipo de entidade inválido: §7" + args[0]);
                FSound.fail(p);
                return true;
            }
        }
        int killed = 0;
        for (Entity e : p.getNearbyEntities(radius, radius, radius)) {
            if (!(e instanceof Mob mob) || (type != null && e.getType() != type)) continue;
            mob.remove();
            killed++;
        }
        p.sendMessage("§eMataste §7" + killed + " §emob(s) num raio de §7" + radius + " §eblocos.");
        FSound.success(p);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length != 1) return List.of();
        String prefix = args[0].toLowerCase(Locale.ROOT);
        List<String> out = new ArrayList<>();
        out.add("all");
        Registry.ENTITY_TYPE.stream()
                .filter(t -> t != EntityType.PLAYER && t.getEntityClass() != null && Mob.class.isAssignableFrom(t.getEntityClass()))
                .forEach(t -> out.add(t.getKey().getKey()));
        return out.stream().filter(n -> n.startsWith(prefix)).sorted().toList();
    }
}
