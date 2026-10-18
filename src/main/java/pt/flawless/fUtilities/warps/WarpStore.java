package pt.flawless.fUtilities.warps;

import org.bukkit.configuration.ConfigurationSection;
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

    public static String normalize(String name) {
        return name.trim().toLowerCase();
    }

    public List<Warp> all() {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        List<Warp> warps = new ArrayList<>();
        for (String key : yaml.getKeys(false)) {
            ConfigurationSection s = yaml.getConfigurationSection(key);
            if (s != null) warps.add(read(key, s));
        }
        warps.sort(Comparator.comparing(Warp::name));
        return warps;
    }

    public Optional<Warp> get(String name) {
        String key = normalize(name);
        return all().stream().filter(w -> w.name().equals(key)).findFirst();
    }

    public void save(Warp warp) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        String key = normalize(warp.name());
        yaml.set(key, null);
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
        write(yaml);
    }

    public boolean delete(String name) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
        String key = normalize(name);
        if (!yaml.contains(key)) return false;
        yaml.set(key, null);
        write(yaml);
        return true;
    }

    private Warp read(String key, ConfigurationSection s) {
        String[] icon = s.getString("icone", "ENDER_PEARL").split(":");
        short data = icon.length > 1 ? Short.parseShort(icon[1].trim()) : 0;
        return new Warp(key, s.getString("mundo"), s.getDouble("x"), s.getDouble("y"), s.getDouble("z"),
                (float) s.getDouble("yaw"), (float) s.getDouble("pitch"), icon[0].trim(), data,
                s.getBoolean("ativo", true), s.getString("grupo"));
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
