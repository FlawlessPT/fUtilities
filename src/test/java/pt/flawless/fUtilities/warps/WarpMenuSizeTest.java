package pt.flawless.fUtilities.warps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WarpMenuSizeTest {
    @Test
    void roundsUpToRowsWithLimits() {
        assertEquals(9, WarpMenuSize.forCount(0));
        assertEquals(9, WarpMenuSize.forCount(9));
        assertEquals(18, WarpMenuSize.forCount(10));
        assertEquals(54, WarpMenuSize.forCount(54));
        assertEquals(54, WarpMenuSize.forCount(100));
    }
}
