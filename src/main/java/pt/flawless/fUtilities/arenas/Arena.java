package pt.flawless.fUtilities.arenas;

import java.util.List;

/** Arena {@code /arena}: {@code id} é o nome em minúsculas; {@code nome}, {@code lore} e {@code item} aparecem no menu. */
public record Arena(String id, String world, double x, double y, double z, float yaw, float pitch,
                    String nome, List<String> lore, String item) {
}
