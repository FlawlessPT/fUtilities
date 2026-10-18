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
        return new Warp(name, "world", 1.5, 64, -3.5, 90f, 10f, "DIAMOND", (short) 2, false, "vip", List.of(), List.of(), null);
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
        assertEquals("Spawn", s.get("spawn").orElseThrow().name());
        s.save(warp("spawn"));
        assertEquals(1, s.all().size());
        assertTrue(s.delete("SPAWN"));
        assertTrue(s.all().isEmpty());
    }

    private WarpStore fromYaml(String yaml) throws Exception {
        File f = new File(dir, "warps.yml");
        Files.writeString(f.toPath(), yaml);
        return new WarpStore(f);
    }

    @Test
    void keepsExactNameAsTyped() throws Exception {
        WarpStore s = store();
        s.save(warp("Spawn"));
        assertEquals("Spawn", s.all().get(0).name());
        assertTrue(Files.readString(new File(dir, "sub/warps.yml").toPath()).contains("Spawn:"));
    }

    @Test
    void lookupIgnoresCase() {
        WarpStore s = store();
        s.save(warp("Spawn"));
        assertTrue(s.get("SPAWN").isPresent());
        assertTrue(s.get("spawn").isPresent());
    }

    @Test
    void saveReplacesCaseInsensitiveKeyWithNewSpelling() {
        WarpStore s = store();
        s.save(warp("spawn"));
        s.save(warp("Spawn"));
        assertEquals(1, s.all().size());
        assertEquals("Spawn", s.all().get(0).name());
    }

    @Test
    void deleteIgnoresCase() {
        WarpStore s = store();
        s.save(warp("Spawn"));
        assertTrue(s.delete("sPAWN"));
        assertTrue(s.all().isEmpty());
    }

    @Test
    void legacyLowercaseFileStillWorks() throws Exception {
        WarpStore s = fromYaml("spawn:\n  mundo: w\n");
        assertEquals("spawn", s.get("Spawn").orElseThrow().name());
    }

    @Test
    void duplicateKeysByCaseDoNotCrash() throws Exception {
        WarpStore s = fromYaml("Spawn:\n  mundo: w\nspawn:\n  mundo: w\n");
        assertEquals(2, s.all().size());
        assertTrue(s.get("SPAWN").isPresent());
    }

    @Test
    void descriptionReadAsList() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\n  descricao:\n    - '&aOla'\n    - linha 2\n");
        assertEquals(List.of("&aOla", "linha 2"), s.get("a").orElseThrow().description());
    }

    @Test
    void descriptionReadFromPlainString() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\n  descricao: Uma linha\n");
        assertEquals(List.of("Uma linha"), s.get("a").orElseThrow().description());
    }

    @Test
    void missingOrInvalidDescriptionIsEmpty() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\nb:\n  mundo: w\n  descricao: []\nc:\n  mundo: w\n  descricao:\n    - 5\n");
        assertTrue(s.get("a").orElseThrow().description().isEmpty());
        assertTrue(s.get("b").orElseThrow().description().isEmpty());
        assertTrue(s.get("c").orElseThrow().description().isEmpty());
    }

    @Test
    void descriptionSurvivesSave() {
        WarpStore s = store();
        Warp w = new Warp("a", "w", 0, 0, 0, 0, 0, "STONE", (short) 0, true, null, List.of("x", "y"), List.of(), null);
        s.save(w);
        assertEquals(w, s.get("a").orElseThrow());
    }

    @Test
    void aliasReadAsList() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\n  alias:\n    - loja\n    - mercado\n");
        assertEquals(List.of("loja", "mercado"), s.get("a").orElseThrow().aliases());
    }

    @Test
    void aliasReadAsPlainString() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\n  alias: loja\n");
        assertEquals(List.of("loja"), s.get("a").orElseThrow().aliases());
    }

    @Test
    void aliasStripsSlashAndRejectsInvalid() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\n  alias:\n    - '/loja'\n    - 'a b'\n    - ''\n    - 5\n    - ' x '\n");
        assertEquals(List.of("loja", "x"), s.get("a").orElseThrow().aliases());
        WarpStore n = fromYaml("a:\n  mundo: w\nb:\n  mundo: w\n  alias: []\nc:\n  mundo: w\n  alias: 7\n");
        assertTrue(n.get("a").orElseThrow().aliases().isEmpty());
        assertTrue(n.get("b").orElseThrow().aliases().isEmpty());
        assertTrue(n.get("c").orElseThrow().aliases().isEmpty());
    }

    @Test
    void findByAliasIgnoresCaseAndSkipsInactive() throws Exception {
        WarpStore s = fromYaml("a:\n  mundo: w\n  alias: Loja\nb:\n  mundo: w\n  ativo: false\n  alias: off\n");
        assertEquals("a", s.findByAlias("LOJA").orElseThrow().name());
        assertTrue(s.findByAlias("off").isEmpty());
        assertTrue(s.findByAlias("nada").isEmpty());
    }

    @Test
    void findByAliasFirstWinsOnDuplicates() throws Exception {
        WarpStore s = fromYaml("b:\n  mundo: w\n  alias: x\na:\n  mundo: w\n  alias: x\n");
        assertEquals("a", s.findByAlias("x").orElseThrow().name());
    }

    @Test
    void aliasSurvivesSave() throws Exception {
        WarpStore s = store();
        Warp w = new Warp("a", "w", 0, 0, 0, 0, 0, "STONE", (short) 0, true, null, List.of(), List.of("loja", "m"), null);
        s.save(w);
        assertEquals(w, s.get("a").orElseThrow());
        s.save(new Warp("a", "w", 0, 0, 0, 0, 0, "STONE", (short) 0, true, null, List.of(), List.of(), null));
        assertTrue(s.get("a").orElseThrow().aliases().isEmpty());
        assertFalse(Files.readString(new File(dir, "sub/warps.yml").toPath()).contains("alias"));
    }

    private Warp readYaml(String body) throws Exception {
        File f = new File(dir, "warps.yml");
        Files.writeString(f.toPath(), "a:\n  mundo: w\n" + body);
        return new WarpStore(f).get("a").orElseThrow();
    }

    @Test
    void titleReadFromFile() throws Exception {
        assertEquals("&6&lLoja", readYaml("  titulo: \"&6&lLoja\"\n").title());
    }

    @Test
    void missingBlankOrInvalidTitleIsNull() throws Exception {
        assertNull(readYaml("").title());
        assertNull(readYaml("  titulo: \"\"\n").title());
        assertNull(readYaml("  titulo: \"  \"\n").title());
        assertNull(readYaml("  titulo: 5\n").title());
    }

    @Test
    void nullTitleNotWrittenOnSave() throws Exception {
        store().save(warp("a"));
        assertFalse(Files.readString(new File(dir, "sub/warps.yml").toPath()).contains("titulo"));
    }

    @Test
    void titleSurvivesSave() {
        WarpStore s = store();
        Warp w = warp("a");
        s.save(new Warp(w.name(), w.world(), w.x(), w.y(), w.z(), w.yaw(), w.pitch(), w.material(), w.data(),
                w.active(), w.group(), w.description(), w.aliases(), "&b&lA"));
        assertEquals("&b&lA", s.get("a").orElseThrow().title());
    }
}
