package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.chests.ChestStore;
import pt.flawless.fUtilities.chests.PersonalChest;
import pt.flawless.fapi.sounds.FSound;

public class BauCommand implements CommandExecutor {
    /** Quantos baús existem. O 1 é de todos (futilities.bau); o N>1 pede futilities.bau.N, para vender à parte. */
    public static final int MAX_CHESTS = 2;

    private final ChestStore store;

    public BauCommand(ChestStore store) {
        this.store = store;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cSó jogadores podem usar este comando.");
            return true;
        }
        if (args.length > 1) {
            p.sendMessage("§cUso: /bau [número]");
            return true;
        }
        int number = 1;
        if (args.length == 1) {
            try {
                number = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                number = 0;
            }
            if (number < 1 || number > MAX_CHESTS) {
                p.sendMessage("§cBaú inexistente. Escolhe de 1 a " + MAX_CHESTS + ".");
                FSound.fail(p);
                return true;
            }
        }
        String perm = number == 1 ? "futilities.bau" : "futilities.bau." + number;
        if (!p.hasPermission(perm)) {
            p.sendMessage("§cNão tens o baú " + number + ".");
            FSound.fail(p);
            return true;
        }
        p.openInventory(new PersonalChest(p.getUniqueId(), number, store.load(p.getUniqueId(), number)).getInventory());
        return true;
    }
}
