package pt.flawless.FUtilities.managers;

import org.bukkit.ChatColor;

public class MessagesManager
{

    public static String semPermissão = "§cNão possuis a permissão requerida para usar este comando!";

    public static String jogadorOff = "§cJogador offline!";

    public static String onlyPlayers = "§cEste comando só pode ser executado por jogadores!";

    public static String vazio = "§e";

    //FLY

    public static String flyOn = "§eModo de voou §7Ativado§e!";

    public static String flyOff = "§cModo de voou §7Desativado§c!";

    //CHAT

    public static String chatOff = "§cO chat está desativado!";

    //CLEAR

    public static String inventarioLimpo = "§eTodos os teus itens do inventário foram removidos!";

    //GAMEMODE

    public static String gm0 = "§eO teu modo de jogo foi alterado para §7Sobrevivência§e!";

    public static String gm1 = "§eO teu modo de jogo foi alterado para §7Criativo§e!";

    public static String gm2 = "§eO teu modo de jogo foi alterado para §7Aventura§e!";

    public static String gm3 = "§eO teu modo de jogo foi alterado para §7Espectador§e!";

    //REGEN

    public static String vida = "§eA tua vida foi completamente regenerada!";

    public static String vidaFull = "§cA tua vida não foi regenerada por já estares com a vida cheia!";

    public static String fome = "§eA tua fome foi completamente regenerada!";

    public static String fomeFull = "§cA tua fome não foi saciada por já estares com a fome cheia!";

    public static String heal = "§eA tua fome e vida foram completamente regeneradas!";

    //DATABASE-MESSAGES

    //	public static String sqlPrefix = ChatColor.GREEN + "[SQLITE] ";
    public static String mySQLPrefix = ChatColor.GREEN + "[BarberiansUtils] ";
    public static String databasePrefix = ChatColor.GREEN + "[BarberiansUtils] ";
    public static String mySqlConnectionSucess = mySQLPrefix + ChatColor.GRAY + "Ligação efetuada com sucesso!";
    public static String mySqlConnectionFail = mySQLPrefix + ChatColor.RED + "Ligação falhada!";
    //	public static String sqlConnectionSucess = sqlPrefix + ChatColor.GRAY + "Ligação efetuada com sucesso!";
    //	public static String sqlConnectionFail = sqlPrefix + ChatColor.RED + "Ligação falhada, plugin desativado!";
    public static String tableLoadSucess = databasePrefix + ChatColor.GRAY + "Tabela 'Reports' criada com sucesso!";
    public static String tableLoadFail = databasePrefix + ChatColor.RED + "A criação da tabela 'Reports' falhou!";
    public static String connectionClosedSucess = databasePrefix + ChatColor.GRAY + "Ligação encerrada!";
    public static String connectionClosedFail = databasePrefix + ChatColor.RED + "Tentativa de encerramento de ligação falhada!";

}
