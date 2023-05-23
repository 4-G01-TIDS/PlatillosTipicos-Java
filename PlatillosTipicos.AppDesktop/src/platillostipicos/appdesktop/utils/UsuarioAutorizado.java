/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platillostipicos.appdesktop.utils;

/**
 *
 * @author edefl
 */
public class UsuarioAutorizado {

    private static int id;
    private static String login;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        UsuarioAutorizado.id = id;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        UsuarioAutorizado.login = login;
    }
}

