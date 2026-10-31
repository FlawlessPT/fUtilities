package pt.flawless.fUtilities.helpers;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/** Vai buscar UUID e textura de um jogador diretamente à Mojang (o {@code PlayerProfile#update} do Paper não consulta a Mojang com {@code online-mode=false}). */
public final class SkinLookup {
    private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

    private SkinLookup() {
    }

    /** Completa com o perfil com textura, ou com {@code null} se o nome não existir ou a Mojang falhar. */
    public static CompletableFuture<PlayerProfile> lookup(String name) {
        String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8);
        return get("https://api.mojang.com/users/profiles/minecraft/" + encoded)
                .thenCompose(body -> {
                    if (body == null) return CompletableFuture.completedFuture((PlayerProfile) null);
                    JsonObject user = JsonParser.parseString(body).getAsJsonObject();
                    String rawId = user.get("id").getAsString();
                    String realName = user.get("name").getAsString();
                    UUID id = UUID.fromString(rawId.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
                    return get("https://sessionserver.mojang.com/session/minecraft/profile/" + rawId + "?unsigned=false")
                            .thenApply(profileBody -> toProfile(id, realName, profileBody));
                })
                .exceptionally(error -> null);
    }

    private static PlayerProfile toProfile(UUID id, String name, String body) {
        if (body == null) return null;
        JsonArray properties = JsonParser.parseString(body).getAsJsonObject().getAsJsonArray("properties");
        PlayerProfile profile = Bukkit.createProfile(id, name);
        for (JsonElement element : properties) {
            JsonObject property = element.getAsJsonObject();
            if (!property.get("name").getAsString().equals("textures")) continue;
            String signature = property.has("signature") ? property.get("signature").getAsString() : null;
            profile.setProperty(new ProfileProperty("textures", property.get("value").getAsString(), signature));
        }
        return profile.hasTextures() ? profile : null;
    }

    /** Corpo da resposta 200, ou {@code null} para qualquer outro código. */
    private static CompletableFuture<String> get(String url) {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(8)).GET().build();
        return HTTP.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> response.statusCode() == 200 ? response.body() : null);
    }
}
