package pt.flawless.fUtilities.arenas;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import pt.flawless.fapi.sounds.FSound;

public final class ArenaTeleporter {
    private ArenaTeleporter() {
    }

    public static void teleport(Player p, ArenaStore store, String id) {
        Arena a = store.get(id).orElse(null);
        if (a == null) {
            p.sendMessage("§cArena inexistente.");
            FSound.fail(p);
            return;
        }
        World w = a.world() == null ? null : Bukkit.getWorld(a.world());
        if (w == null) {
            p.sendMessage("§cO mundo da arena não está carregado.");
            FSound.fail(p);
            return;
        }
        p.teleport(new Location(w, a.x(), a.y(), a.z(), a.yaw(), a.pitch()));
        p.sendMessage("§aTeleportado para " + a.nome().replace('&', '§') + "§a.");
        FSound.success(p);
    }
}
