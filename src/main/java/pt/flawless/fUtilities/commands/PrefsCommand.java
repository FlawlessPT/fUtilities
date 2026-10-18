package pt.flawless.fUtilities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pt.flawless.fUtilities.menus.PrefsMenu;
import pt.flawless.fapi.chat.ChatApi;

/** {@code /prefs}: abre o menu de preferências. {@code chat} é nulo se o fChat não estiver disponível. */
public class PrefsCommand implements CommandExecutor {
    private final ChatApi chat;

    public PrefsCommand(ChatApi chat) {
        this.chat = chat;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Só jogadores podem usar este comando.");
            return true;
        }
        if (chat == null) {
            player.sendMessage("§cPreferências indisponíveis de momento.");
            return true;
        }
        new PrefsMenu(chat, player).open();
        return true;
    }
}
