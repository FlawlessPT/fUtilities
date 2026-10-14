package pt.flawless.fUtilities.managers;

import pt.flawless.fUtilities.FUtilities;

public class VariablesManager
{
    //GENERAL

    public static String comandoCommandPermission = FUtilities.getMainPlugin().getConfig().getString("Permissoes.Comandos.Comandos");
    public static String noPermissionMessage = FUtilities.getMainPlugin().getConfig().getString("Mensagens.SemPermissao").replace("&", "§");

    //MENUS

    public static String titulo = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Titulo").replace("&", "§");

    //MENUS-ITEMS

    public static String nameFly = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Fly.Name").replace("&", "§");
    public static String loreFly = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Fly.Lore").replace("&", "§");

    public static String nameFlyStateOn = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.FlyState.Name_On").replace("&", "§");
    public static String nameFlyStateOff = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.FlyState.Name_Off").replace("&", "§");

    public static String nameRain = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Rain.Name").replace("&", "§");
    public static String loreRain = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Rain.Lore").replace("&", "§");

    public static String nameChat = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Chat.Name").replace("&", "§");
    public static String loreChat = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Chat.Lore").replace("&", "§");

    public static String nameHeal = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Heal.Name").replace("&", "§");
    public static String loreHeal = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Heal.Lore").replace("&", "§");


    public static String nameFiller = FUtilities.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Filler.Name").replace("&", "§");


    //MESSAGES

    public static String flyOnMessage = FUtilities.getMainPlugin().getConfig().getString("Mensagens.FlyOn").replace("&", "§");
    public static String flyOffMessage = FUtilities.getMainPlugin().getConfig().getString("Mensagens.FlyOff").replace("&", "§");
    public static String chatClearedMessage = FUtilities.getMainPlugin().getConfig().getString("Mensagens.ChatCleared").replace("&", "§");
    public static String healMessage = FUtilities.getMainPlugin().getConfig().getString("Mensagens.Heal").replace("&", "§");
}
