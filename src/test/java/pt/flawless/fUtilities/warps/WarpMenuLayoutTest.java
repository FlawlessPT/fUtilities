package pt.flawless.fUtilities.warps;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarpMenuLayoutTest {
    @Test
    void innerSlotsAre28InRowMajorOrder() {
        List<Integer> s = WarpMenuLayout.innerSlots();
        assertEquals(28, s.size());
        assertEquals(10, s.get(0));
        assertEquals(43, s.get(27));
        assertEquals(28, new HashSet<>(s).size());
        assertEquals(s.stream().sorted().toList(), s);
    }

    @Test
    void borderSlotsAreExactlyTheComplement() {
        List<Integer> inner = WarpMenuLayout.innerSlots();
        for (int i = 0; i < WarpMenuLayout.SIZE; i++) assertEquals(!inner.contains(i), WarpMenuLayout.isBorder(i));
    }

    @Test
    void capacityIs28() {
        assertEquals(28, WarpMenuLayout.capacity());
    }
}
