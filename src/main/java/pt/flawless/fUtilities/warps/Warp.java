package pt.flawless.fUtilities.warps;

import java.util.List;

/** {@code name} é a grafia original; {@code description} e {@code aliases} nunca são nulos; {@code title} (título completo do item no
 * menu, com códigos {@code &}) é nulo quando não há título personalizado. */
public record Warp(String name, String world, double x, double y, double z, float yaw, float pitch,
                   String material, boolean active, String group, List<String> description, List<String> aliases, String title) {
    public Warp {
        description = description == null ? List.of() : List.copyOf(description);
        aliases = aliases == null ? List.of() : List.copyOf(aliases);
    }
}
