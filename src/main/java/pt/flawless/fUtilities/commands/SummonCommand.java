package pt.flawless.fUtilities.commands;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

import java.util.List;
import java.util.Locale;

/** {@code /summon <entidade> [x y z]}: invoca uma entidade na tua posição ou nas coordenadas dadas (aceita {@code ~}). */
public class SummonCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length != 1 && args.length != 4) {
            p.sendMessage("§cUso: /summon <entidade> [x y z]");
            return true;
        }
        EntityType type = parse(args[0]);
        if (type == null) {
            p.sendMessage("§cEntidade inválida ou que não pode ser invocada: §7" + args[0]);
            FSound.fail(p);
            return true;
        }
        Location loc = p.getLocation();
        if (args.length == 4) {
            Double x = coord(args[1], loc.getX());
            Double y = coord(args[2], loc.getY());
            Double z = coord(args[3], loc.getZ());
            if (x == null || y == null || z == null) {
                p.sendMessage("§cUso: /summon <entidade> [x y z]");
                return true;
            }
            loc.setX(x);
            loc.setY(y);
            loc.setZ(z);
        }
        try {
            p.getWorld().spawnEntity(loc, type);
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            p.sendMessage("§cNão foi possível invocar §7" + args[0] + "§c.");
            FSound.fail(p);
            return true;
        }
        p.sendMessage("§aInvocaste um(a) §7" + type.getKey().getKey() + "§a.");
        FSound.success(p);
        return true;
    }

    private static EntityType parse(String name) {
        NamespacedKey key = NamespacedKey.fromString(name.toLowerCase(Locale.ROOT));
        if (key == null) return null;
        EntityType type = Registry.ENTITY_TYPE.get(key);
        return type != null && type != EntityType.PLAYER && type.isSpawnable() ? type : null;
    }

    /** Número ou {@code ~}/{@code ~n} relativo a {@code base}; {@code null} se inválido. */
    private static Double coord(String s, double base) {
        try {
            double v;
            if (s.startsWith("~")) v = base + (s.length() == 1 ? 0 : Double.parseDouble(s.substring(1)));
            else v = Double.parseDouble(s);
            return Double.isFinite(v) ? v : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            String prefix = args[0].toLowerCase(Locale.ROOT);
            return Registry.ENTITY_TYPE.stream()
                    .filter(t -> t != EntityType.PLAYER && t.isSpawnable())
                    .map(t -> t.getKey().getKey())
                    .filter(n -> n.startsWith(prefix))
                    .sorted()
                    .toList();
        }
        return args.length <= 4 ? List.of("~") : List.of();
    }
}
