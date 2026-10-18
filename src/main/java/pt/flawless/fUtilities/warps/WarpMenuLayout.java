package pt.flawless.fUtilities.warps;

import java.util.ArrayList;
import java.util.List;

/** Layout fixo do menu de warps (54 slots, moldura à volta), sem Bukkit para ser testável. */
public final class WarpMenuLayout {
    public static final int SIZE = 54;
    private static final List<Integer> INNER = new ArrayList<>();

    static {
        for (int s = 0; s < SIZE; s++) if (!isBorder(s)) INNER.add(s);
    }

    private WarpMenuLayout() {}

    public static boolean isBorder(int slot) {
        int row = slot / 9, col = slot % 9;
        return row == 0 || row == 5 || col == 0 || col == 8;
    }

    public static List<Integer> innerSlots() {
        return List.copyOf(INNER);
    }

    public static int capacity() {
        return INNER.size();
    }
}
