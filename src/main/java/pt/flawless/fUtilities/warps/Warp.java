package pt.flawless.fUtilities.warps;

import java.util.List;

/** {@code name} é a grafia original; {@code description}, {@code aliases} e {@code color} nunca são nulos. */
public record Warp(String name, String world, double x, double y, double z, float yaw, float pitch,
                   String material, short data, boolean active, String group, List<String> description, List<String> aliases, String color) {
    public Warp {
        description = description == null ? List.of() : List.copyOf(description);
        aliases = aliases == null ? List.of() : List.copyOf(aliases);
        color = color == null || color.isBlank() ? "&e" : color;
    }
}
