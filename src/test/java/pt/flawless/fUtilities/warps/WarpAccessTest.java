package pt.flawless.fUtilities.warps;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class WarpAccessTest {

    private static Function<String, List<String>> inheritance(Map<String, List<String>> inherits) {
        Map<String, List<String>> norm = new HashMap<>();
        for (var entry : inherits.entrySet()) {
            norm.put(entry.getKey().toLowerCase(),
                    entry.getValue().stream().map(String::toLowerCase).toList());
        }
        return group -> norm.getOrDefault(group, List.of());
    }

    @Test
    void freeWarpAllowsEveryone() {
        var inherits = inheritance(Map.of());
        assertTrue(WarpAccess.allowed(null, Set.of("player"), inherits));
        assertTrue(WarpAccess.allowed("", Set.of("player"), inherits));
        assertTrue(WarpAccess.allowed(null, Set.of(), inherits));
    }

    @Test
    void directGroupAllowed() {
        var inherits = inheritance(Map.of());
        assertTrue(WarpAccess.allowed("member", Set.of("member"), inherits));
        assertTrue(WarpAccess.allowed("vip", Set.of("member", "vip"), inherits));
    }

    @Test
    void inheritedGroupAllowed() {
        var inherits = inheritance(Map.of("vip", List.of("member")));
        assertTrue(WarpAccess.allowed("member", Set.of("vip"), inherits));
    }

    @Test
    void transitiveInheritanceAllowed() {
        var inherits = inheritance(Map.of(
                "diamond", List.of("platinum"),
                "platinum", List.of("gold"),
                "gold", List.of("member")
        ));
        assertTrue(WarpAccess.allowed("member", Set.of("diamond"), inherits));
        assertTrue(WarpAccess.allowed("gold", Set.of("diamond"), inherits));
        assertTrue(WarpAccess.allowed("platinum", Set.of("diamond"), inherits));
    }

    @Test
    void otherGroupDenied() {
        var inherits = inheritance(Map.of());
        assertFalse(WarpAccess.allowed("member", Set.of("admin"), inherits));
        assertFalse(WarpAccess.allowed("vip", Set.of("member"), inherits));
    }

    @Test
    void caseInsensitive() {
        var inherits = inheritance(Map.of("VIP", List.of("MEMBER")));
        assertTrue(WarpAccess.allowed("member", Set.of("vip"), inherits));
        assertTrue(WarpAccess.allowed("MEMBER", Set.of("VIP"), inherits));
        assertTrue(WarpAccess.allowed("MeMbEr", Set.of("ViP"), inherits));
    }

    @Test
    void inheritanceCycleTerminates() {
        var inherits = inheritance(Map.of(
                "a", List.of("b"),
                "b", List.of("a")
        ));
        assertFalse(WarpAccess.allowed("c", Set.of("a"), inherits));
        assertFalse(WarpAccess.allowed("c", Set.of("b"), inherits));
    }

    @Test
    void noGroupsDenied() {
        var inherits = inheritance(Map.of());
        assertFalse(WarpAccess.allowed("member", Set.of(), inherits));
        assertFalse(WarpAccess.allowed("vip", Set.of(), inherits));
    }
}
