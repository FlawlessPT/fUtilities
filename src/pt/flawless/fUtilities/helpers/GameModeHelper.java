package pt.flawless.fUtilities.helpers;

import org.bukkit.GameMode;

public class GameModeHelper {
    public static String getGameModeLabel(GameMode currentGameMode) {
        switch (currentGameMode) {
            case CREATIVE -> {
                return "Criativo";
            }
            case SURVIVAL -> {
                return "Sobrevivência";
            }
            case SPECTATOR -> {
                return "Espectador";
            }
            case ADVENTURE -> {
                return "Aventura";
            }
            default -> {
                return "Inválido";
            }
        }
    }
}