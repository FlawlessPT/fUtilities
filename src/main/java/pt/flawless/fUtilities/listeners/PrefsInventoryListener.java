package pt.flawless.fUtilities.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.Plugin;
import pt.flawless.fUtilities.menus.PrefsMenu;
import pt.flawless.fapi.chat.Channel;
import pt.flawless.fapi.sounds.FSound;

public class PrefsInventoryListener implements Listener {
    private final Plugin plugin;

    public PrefsInventoryListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof PrefsMenu menu)) return;
        e.setCancelled(true);
        if (e.getRawSlot() != PrefsMenu.CHAT_SLOT) return;

        Player player = menu.getPlayer();
        Channel next = menu.getChat().getDefaultChannel(player.getUniqueId()) == Channel.GLOBAL
                ? Channel.LOCAL : Channel.GLOBAL;
        menu.getChat().setDefaultChannel(player.getUniqueId(), next).whenComplete((v, error) -> {
            // Completa fora da thread principal: voltar a ela antes de mexer no inventário.
            try {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    if (error != null) {
                        plugin.getLogger().warning("Falha ao gravar o chat padrão: " + error);
                        player.sendMessage("§cNão foi possível guardar a preferência.");
                        FSound.fail(player);
                        return;
                    }
                    menu.render();
                    FSound.success(player);
                });
            } catch (RuntimeException ex) {
                // plugin a desligar
            }
        });
    }

    @EventHandler
    public void onDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof PrefsMenu) e.setCancelled(true);
    }
}
