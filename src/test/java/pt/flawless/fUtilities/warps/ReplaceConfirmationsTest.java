package pt.flawless.fUtilities.warps;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

class ReplaceConfirmationsTest {
    private final AtomicLong now = new AtomicLong(1000);
    private final ReplaceConfirmations c = new ReplaceConfirmations(now::get, 15000);
    private final UUID p = UUID.randomUUID();

    private static Warp w(String name) {
        return new Warp(name, "world", 0, 0, 0, 0, 0, "STONE", (short) 0, true, null, List.of(), List.of(), null);
    }

    @Test
    void takeReturnsPendingOnce() {
        c.request(p, w("A"));
        assertEquals("A", c.take(p).orElseThrow().name());
        assertTrue(c.take(p).isEmpty());
    }

    @Test
    void takeAfterTtlReturnsEmpty() {
        c.request(p, w("A"));
        now.addAndGet(15001);
        assertTrue(c.take(p).isEmpty());
    }

    @Test
    void newRequestReplacesOld() {
        c.request(p, w("A"));
        c.request(p, w("B"));
        assertEquals("B", c.take(p).orElseThrow().name());
    }

    @Test
    void cancelRemoves() {
        c.request(p, w("A"));
        c.cancel(p);
        assertTrue(c.take(p).isEmpty());
    }

    @Test
    void isYesAcceptsTrimAndCase() {
        assertTrue(ReplaceConfirmations.isYes("sim"));
        assertTrue(ReplaceConfirmations.isYes(" SIM "));
        assertTrue(ReplaceConfirmations.isYes("Sim"));
        for (String s : new String[]{"nao", "", "sim sim", "s"}) assertFalse(ReplaceConfirmations.isYes(s), s);
    }
}
