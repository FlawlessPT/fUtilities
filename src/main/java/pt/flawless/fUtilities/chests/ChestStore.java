package pt.flawless.fUtilities.chests;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/** Guarda os baús pessoais em {@code plugins/fUtilities/chests/<uuid>.yml}, chave = número do baú. */
public class ChestStore {
    private final File dir;

    public ChestStore(File dataFolder) {
        this.dir = new File(dataFolder, "chests");
    }

    /** Conteúdo gravado do baú, ou nulo se nunca foi usado. */
    public ItemStack[] load(UUID owner, int number) {
        File file = new File(dir, owner + ".yml");
        if (!file.exists()) return null;
        List<?> list = YamlConfiguration.loadConfiguration(file).getList(String.valueOf(number));
        if (list == null) return null;
        ItemStack[] items = new ItemStack[PersonalChest.SIZE];
        for (int i = 0; i < items.length && i < list.size(); i++) {
            if (list.get(i) instanceof ItemStack item) items[i] = item;
        }
        return items;
    }

    public void save(UUID owner, int number, ItemStack[] contents) throws IOException {
        dir.mkdirs();
        File file = new File(dir, owner + ".yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        yml.set(String.valueOf(number), Arrays.asList(contents));
        yml.save(file);
    }
}
