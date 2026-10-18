package pt.flawless.fUtilities.warps;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.LongSupplier;

/** Pedidos de substituição de warp à espera de "sim". Thread-safe (chat assíncrono + thread principal). */
public class ReplaceConfirmations {
    private record Pending(Warp warp, long expiresAt) {}

    private final Map<UUID, Pending> pending = new ConcurrentHashMap<>();
    private final LongSupplier nowMillis;
    private final long ttlMillis;

    public ReplaceConfirmations(LongSupplier nowMillis, long ttlMillis) {
        this.nowMillis = nowMillis;
        this.ttlMillis = ttlMillis;
    }

    public void request(UUID player, Warp replacement) {
        pending.put(player, new Pending(replacement, nowMillis.getAsLong() + ttlMillis));
    }

    /** Remove e devolve o pedido se existir e não tiver expirado. */
    public Optional<Warp> take(UUID player) {
        Pending p = pending.remove(player);
        return p == null || nowMillis.getAsLong() > p.expiresAt() ? Optional.empty() : Optional.of(p.warp());
    }

    public void cancel(UUID player) {
        pending.remove(player);
    }

    public static boolean isYes(String message) {
        return message.trim().equalsIgnoreCase("sim");
    }
}
