package pt.flawless.fUtilities.warps;

import java.util.*;
import java.util.function.Function;

public final class WarpAccess {

    private WarpAccess() {
    }

    public static boolean allowed(String warpGroup, Set<String> playerGroups, Function<String, List<String>> inherits) {
        if (warpGroup == null || warpGroup.isEmpty()) {
            return true;
        }

        String warpGroupNorm = warpGroup.toLowerCase();
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        for (String group : playerGroups) {
            String norm = group.toLowerCase();
            if (norm.equals(warpGroupNorm)) {
                return true;
            }
            if (visited.add(norm)) {
                queue.add(norm);
            }
        }

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (String inherited : inherits.apply(current)) {
                String norm = inherited.toLowerCase();
                if (norm.equals(warpGroupNorm)) {
                    return true;
                }
                if (visited.add(norm)) {
                    queue.add(norm);
                }
            }
        }

        return false;
    }
}
