package pt.flawless.fUtilities.managers;

import pt.flawless.fUtilities.Main;

public class VariablesManager
{
    //GENERAL

    public static String comandoCommandPermission = Main.getMainPlugin().getConfig().getString("Permissoes.Comandos.Comandos");
    public static String noPermissionMessage = Main.getMainPlugin().getConfig().getString("Mensagens.SemPermissao").replace("&", "§");

    //MENUS

    public static String titulo = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Titulo").replace("&", "§");

    //MENUS-ITEMS

    public static String nameFly = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Fly.Name").replace("&", "§");
    public static String loreFly = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Fly.Lore").replace("&", "§");

    public static String nameFlyStateOn = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.FlyState.Name_On").replace("&", "§");
    public static String nameFlyStateOff = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.FlyState.Name_Off").replace("&", "§");

    public static String nameRain = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Rain.Name").replace("&", "§");
    public static String loreRain = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Rain.Lore").replace("&", "§");

    public static String nameChat = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Chat.Name").replace("&", "§");
    public static String loreChat = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Chat.Lore").replace("&", "§");

    public static String nameHeal = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Heal.Name").replace("&", "§");
    public static String loreHeal = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Heal.Lore").replace("&", "§");


    public static String nameFiller = Main.getMainPlugin().getConfig().getString("Menus.Comandos.Itens.Filler.Name").replace("&", "§");


    //MESSAGES

    public static String flyOnMessage = Main.getMainPlugin().getConfig().getString("Mensagens.FlyOn").replace("&", "§");
    public static String flyOffMessage = Main.getMainPlugin().getConfig().getString("Mensagens.FlyOff").replace("&", "§");
    public static String chatClearedMessage = Main.getMainPlugin().getConfig().getString("Mensagens.ChatCleared").replace("&", "§");
    public static String healMessage = Main.getMainPlugin().getConfig().getString("Mensagens.Heal").replace("&", "§");
}
