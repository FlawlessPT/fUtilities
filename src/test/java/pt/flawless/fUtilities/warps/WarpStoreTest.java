package pt.flawless.fUtilities.warps;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WarpStoreTest {

    @TempDir File dir;

    private WarpStore store() {
        return new WarpStore(new File(dir, "sub/warps.yml"));
    }

    private static Warp warp(String name) {
        return new Warp(name, "world", 1.5, 64, -3.5, 90f, 10f, "DIAMOND", (short) 2, false, "vip");
    }

    @Test
    void savesAndReadsBack() {
        WarpStore s = store();
        s.save(warp("spawn"));
        assertEquals(warp("spawn"), s.get("spawn").orElseThrow());
    }

    @Test
    void allIsSortedByName() {
        WarpStore s = store();
        s.save(warp("b"));
        s.save(warp("c"));
        s.save(warp("a"));
        assertEquals(List.of("a", "b", "c"), s.all().stream().map(Warp::name).toList());
    }

    @Test
    void missingFileIsEmpty() {
        assertTrue(store().all().isEmpty());
        assertTrue(store().get("x").isEmpty());
    }

    @Test
    void entryWithoutIconOrActiveDefaultsToEnderPearlAndActive() throws Exception {
        File f = new File(dir, "warps.yml");
        Files.writeString(f.toPath(), "spawn:\n  mundo: world\n  x: 1.0\n  y: 2.0\n  z: 3.0\n");
        Warp w = new WarpStore(f).get("spawn").orElseThrow();
        assertEquals("ENDER_PEARL", w.material());
        assertEquals(0, w.data());
        assertTrue(w.active());
        assertNull(w.group());
        assertEquals(0f, w.yaw());
    }

    @Test
    void deleteRemovesAndReportsAbsent() {
        WarpStore s = store();
        s.save(warp("spawn"));
        assertTrue(s.delete("spawn"));
        assertFalse(s.delete("spawn"));
        assertTrue(s.get("spawn").isEmpty());
    }

    @Test
    void editingFileByHandIsSeenOnNextRead() throws Exception {
        WarpStore s = store();
        s.save(warp("spawn"));
        File f = new File(dir, "sub/warps.yml");
        Files.writeString(f.toPath(), Files.readString(f.toPath()).replace("ativo: false", "ativo: true"));
        assertTrue(s.get("spawn").orElseThrow().active());
    }

    @Test
    void malformedYamlIsNeverOverwritten() throws Exception {
        File f = new File(dir, "warps.yml");
        String bad = "spawn:\n  mundo: [unclosed\n\t- x: : :\n";
        Files.writeString(f.toPath(), bad);
        WarpStore s = new WarpStore(f);
        assertThrows(RuntimeException.class, () -> s.save(warp("a")));
        assertThrows(RuntimeException.class, () -> s.delete("spawn"));
        assertEquals(bad, Files.readString(f.toPath()));
    }

    @Test
    void invalidIconDataOrMaterialFallsBack() throws Exception {
        File f = new File(dir, "warps.yml");
        Files.writeString(f.toPath(), "a:\n  mundo: w\n  icone: WOOL:x\nb:\n  mundo: w\n  icone: NOPE:3\n");
        WarpStore s = new WarpStore(f);
        assertEquals("WOOL", s.get("a").orElseThrow().material());
        assertEquals(0, s.get("a").orElseThrow().data());
        assertEquals("ENDER_PEARL", s.get("b").orElseThrow().material());
    }

    @Test
    void handWrittenMixedCaseKeyIsReachableAndReplaced() throws Exception {
        File f = new File(dir, "warps.yml");
        Files.writeString(f.toPath(), "Spawn:\n  mundo: w\n");
        WarpStore s = new WarpStore(f);
        assertEquals("spawn", s.get("spawn").orElseThrow().name());
        s.save(warp("spawn"));
        assertEquals(1, s.all().size());
        assertTrue(s.delete("SPAWN"));
        assertTrue(s.all().isEmpty());
    }

    @Test
    void normalizeLowercasesAndTrims() {
        assertEquals("spawn", WarpStore.normalize("  SpAwN "));
        store().save(warp("Spawn"));
        assertTrue(store().get(" SPAWN").isPresent());
    }
}
