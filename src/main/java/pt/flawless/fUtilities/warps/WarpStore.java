package pt.flawless.fUtilities.warps;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class WarpStore {

    private final File file;

    public WarpStore(File file) {
        this.file = file;
    }


    public List<Warp> all() {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        List<Warp> warps = new ArrayList<>();
        for (String key : yaml.getKeys(false)) {
            ConfigurationSection s = yaml.getConfigurationSection(key);
            if (s != null) warps.add(read(key, s));
        }
        warps.sort(Comparator.comparing(Warp::name, String.CASE_INSENSITIVE_ORDER));
        return warps;
    }

    public Optional<Warp> get(String name) {
        String key = name.trim();
        return all().stream().filter(w -> w.name().equalsIgnoreCase(key)).findFirst();
    }

    public void save(Warp warp) {
        YamlConfiguration yaml = loadStrict();
        String key = warp.name();
        removeKey(yaml, key);
        ConfigurationSection s = yaml.createSection(key);
        s.set("mundo", warp.world());
        s.set("x", warp.x());
        s.set("y", warp.y());
        s.set("z", warp.z());
        s.set("yaw", warp.yaw());
        s.set("pitch", warp.pitch());
        s.set("icone", warp.data() == 0 ? warp.material() : warp.material() + ":" + warp.data());
        s.set("ativo", warp.active());
        s.set("grupo", warp.group());
        s.set("descricao", warp.description().isEmpty() ? null : warp.description());
        write(yaml);
    }

    public boolean delete(String name) {
        YamlConfiguration yaml = loadStrict();
        String key = name.trim();
        if (!removeKey(yaml, key)) return false;
        write(yaml);
        return true;
    }

    // Escritas não usam loadConfiguration: YAML inválido viraria config vazia e o save apagava todos os warps.
    private YamlConfiguration loadStrict() {
        YamlConfiguration yaml = new YamlConfiguration();
        if (!file.exists()) return yaml;
        try {
            yaml.load(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InvalidConfigurationException e) {
            throw new IllegalStateException("warps.yml inválido: " + e.getMessage(), e);
        }
        return yaml;
    }

    private boolean removeKey(YamlConfiguration yaml, String key) {
        boolean found = false;
        for (String k : new ArrayList<>(yaml.getKeys(false))) {
            if (k.equalsIgnoreCase(key)) {
                yaml.set(k, null);
                found = true;
            }
        }
        return found;
    }

    private Warp read(String key, ConfigurationSection s) {
        String[] icon = s.getString("icone", "ENDER_PEARL").split(":");
        short data = 0;
        if (icon.length > 1) {
            try {
                data = Short.parseShort(icon[1].trim());
            } catch (NumberFormatException ignored) {
            }
        }
        String material = Material.getMaterial(icon[0].trim().toUpperCase()) != null
                ? icon[0].trim().toUpperCase() : "ENDER_PEARL";
        return new Warp(key, s.getString("mundo"), s.getDouble("x"), s.getDouble("y"), s.getDouble("z"),
                (float) s.getDouble("yaw"), (float) s.getDouble("pitch"), material, data,
                s.getBoolean("ativo", true), s.getString("grupo"), description(s));
    }

    private static List<String> description(ConfigurationSection s) {
        Object raw = s.get("descricao");
        if (raw instanceof String str) return List.of(str);
        List<String> lines = new ArrayList<>();
        if (raw instanceof List<?> list) for (Object o : list) if (o instanceof String str) lines.add(str);
        return lines;
    }

    private void write(YamlConfiguration yaml) {
        try {
            File parent = file.getParentFile();
            if (parent != null) parent.mkdirs();
            yaml.save(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
