package pt.flawless.fUtilities.arenas;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/** {@code arenas.yml}: chave = id; {@code nome}, {@code lore} e {@code item} editam-se à mão no ficheiro. */
public class ArenaStore {
    private final File file;

    public ArenaStore(File file) {
        this.file = file;
    }

    public List<Arena> all() {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        List<Arena> arenas = new ArrayList<>();
        for (String key : yaml.getKeys(false)) {
            ConfigurationSection s = yaml.getConfigurationSection(key);
            if (s != null) arenas.add(read(key, s));
        }
        arenas.sort((a, b) -> a.id().compareTo(b.id()));
        return arenas;
    }

    public Optional<Arena> get(String id) {
        String key = id.trim().toLowerCase(Locale.ROOT);
        return all().stream().filter(a -> a.id().equals(key)).findFirst();
    }

    /** Grava só a localização; nome, lore e item já editados ficam como estão. */
    public void setLocation(String id, String world, double x, double y, double z, float yaw, float pitch) {
        YamlConfiguration yaml = loadStrict();
        ConfigurationSection s = yaml.getConfigurationSection(id);
        if (s == null) {
            s = yaml.createSection(id);
            s.set("nome", "&e" + id);
            s.set("item", "IRON_SWORD");
        }
        s.set("mundo", world);
        s.set("x", x);
        s.set("y", y);
        s.set("z", z);
        s.set("yaw", yaw);
        s.set("pitch", pitch);
        try {
            File parent = file.getParentFile();
            if (parent != null) parent.mkdirs();
            yaml.save(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // YAML inválido não pode virar config vazia, senão o save apagava todas as arenas.
    private YamlConfiguration loadStrict() {
        YamlConfiguration yaml = new YamlConfiguration();
        if (!file.exists()) return yaml;
        try {
            yaml.load(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException("arenas.yml inválido: " + e.getMessage(), e);
        }
        return yaml;
    }

    private static Arena read(String key, ConfigurationSection s) {
        String item = s.getString("item", "IRON_SWORD").trim().toUpperCase(Locale.ROOT);
        if (Material.getMaterial(item) == null) item = "IRON_SWORD";
        List<String> lore = new ArrayList<>();
        Object raw = s.get("lore");
        if (raw instanceof String str) lore.add(str);
        else if (raw instanceof List<?> list) for (Object o : list) if (o instanceof String str) lore.add(str);
        return new Arena(key.toLowerCase(Locale.ROOT), s.getString("mundo"), s.getDouble("x"), s.getDouble("y"),
                s.getDouble("z"), (float) s.getDouble("yaw"), (float) s.getDouble("pitch"),
                s.getString("nome", "&e" + key), lore, item);
    }
}
