package pt.flawless.FUtilities.managers;

import pt.flawless.FUtilities.Main;

public class VariablesManager
{
    //GENERAL

    public static String comandoCommandPermission = Main.plugin.getConfig().getString("Permissoes.Comandos.Comandos");
    public static String noPermissionMessage = Main.plugin.getConfig().getString("Mensagens.SemPermissao").replace("&", "§");

    //MENUS

    public static String titulo = Main.plugin.getConfig().getString("Menus.Comandos.Titulo").replace("&", "§");

    //MENUS-ITEMS

    public static String nameFly = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Fly.Name").replace("&", "§");
    public static String loreFly = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Fly.Lore").replace("&", "§");

    public static String nameFlyStateOn = Main.plugin.getConfig().getString("Menus.Comandos.Itens.FlyState.Name_On").replace("&", "§");
    public static String nameFlyStateOff = Main.plugin.getConfig().getString("Menus.Comandos.Itens.FlyState.Name_Off").replace("&", "§");

    public static String nameRain = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Rain.Name").replace("&", "§");
    public static String loreRain = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Rain.Lore").replace("&", "§");

    public static String nameChat = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Chat.Name").replace("&", "§");
    public static String loreChat = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Chat.Lore").replace("&", "§");

    public static String nameHeal = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Heal.Name").replace("&", "§");
    public static String loreHeal = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Heal.Lore").replace("&", "§");


    public static String nameFiller = Main.plugin.getConfig().getString("Menus.Comandos.Itens.Filler.Name").replace("&", "§");


    //MESSAGES

    public static String flyOnMessage = Main.plugin.getConfig().getString("Mensagens.FlyOn").replace("&", "§");
    public static String flyOffMessage = Main.plugin.getConfig().getString("Mensagens.FlyOff").replace("&", "§");
    public static String chatClearedMessage = Main.plugin.getConfig().getString("Mensagens.ChatCleared").replace("&", "§");
    public static String healMessage = Main.plugin.getConfig().getString("Mensagens.Heal").replace("&", "§");
}
