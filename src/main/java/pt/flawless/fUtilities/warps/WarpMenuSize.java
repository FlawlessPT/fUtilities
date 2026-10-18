package pt.flawless.fUtilities.warps;

/** Tamanho do menu de warps, sem Bukkit para ser testável. */
public final class WarpMenuSize {
    private WarpMenuSize() {}

    /** Menor múltiplo de 9 que cabe {@code count} itens: mín. 9, máx. 54. */
    public static int forCount(int count) {
        int rows = (count + 8) / 9;
        return Math.max(1, Math.min(6, rows)) * 9;
    }
}
