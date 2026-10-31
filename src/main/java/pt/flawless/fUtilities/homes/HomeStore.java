package pt.flawless.fUtilities.homes;

import org.bukkit.Location;
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
import java.util.UUID;

/** {@code homes.yml}: {@code <uuid>.<nome-minúsculas>}; a ordem do ficheiro é a ordem das homes. */
public class HomeStore {
    private final File file;

    public HomeStore(File file) {
        this.file = file;
    }

    public List<Home> all(UUID owner) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection s = yaml.getConfigurationSection(owner.toString());
        List<Home> homes = new ArrayList<>();
        if (s == null) return homes;
        for (String key : s.getKeys(false)) {
            ConfigurationSection h = s.getConfigurationSection(key);
            if (h == null) continue;
            homes.add(new Home(key, h.getString("mundo"), h.getDouble("x"), h.getDouble("y"), h.getDouble("z"),
                    (float) h.getDouble("yaw"), (float) h.getDouble("pitch")));
        }
        return homes;
    }

    public Optional<Home> get(UUID owner, String name) {
        String key = name.trim().toLowerCase(Locale.ROOT);
        return all(owner).stream().filter(h -> h.name().equals(key)).findFirst();
    }

    public void set(UUID owner, String name, Location loc) {
        YamlConfiguration yaml = loadStrict();
        String path = owner + "." + name.trim().toLowerCase(Locale.ROOT);
        yaml.set(path + ".mundo", loc.getWorld().getName());
        yaml.set(path + ".x", loc.getX());
        yaml.set(path + ".y", loc.getY());
        yaml.set(path + ".z", loc.getZ());
        yaml.set(path + ".yaw", loc.getYaw());
        yaml.set(path + ".pitch", loc.getPitch());
        save(yaml);
    }

    public boolean delete(UUID owner, String name) {
        YamlConfiguration yaml = loadStrict();
        String path = owner + "." + name.trim().toLowerCase(Locale.ROOT);
        if (!yaml.isConfigurationSection(path)) return false;
        yaml.set(path, null);
        if (yaml.getConfigurationSection(owner.toString()).getKeys(false).isEmpty()) yaml.set(owner.toString(), null);
        save(yaml);
        return true;
    }

    private void save(YamlConfiguration yaml) {
        try {
            File parent = file.getParentFile();
            if (parent != null) parent.mkdirs();
            yaml.save(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // YAML inválido não pode virar config vazia, senão o save apagava todas as homes.
    private YamlConfiguration loadStrict() {
        YamlConfiguration yaml = new YamlConfiguration();
        if (!file.exists()) return yaml;
        try {
            yaml.load(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException("homes.yml inválido: " + e.getMessage(), e);
        }
        return yaml;
    }
}
