/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package platillostipicos.appdesktop.Publication;

import java.awt.Component;
import java.awt.Container;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import platillostipicos.accesoadatos.CommentLikeDAL;
import platillostipicos.entidadesdenegocio.Comment;
import platillostipicos.entidadesdenegocio.CommentLike;

/**
 *
 * @author edefl
 */
public class PublicationUtils {

    public static void setThumbsUpButtonIcon(JButton button, boolean voted) {
        if (voted) {
            button.setIcon(new ImageIcon(PublicationUtils.class.getResource("/platillostipicos/appdesktop/utils/imgs/hand-thumbs-up-fill.png")));
        } else {
            button.setIcon(new ImageIcon(PublicationUtils.class.getResource("/platillostipicos/appdesktop/utils/imgs/hand-thumbs-up.png")));
        }
    }

    public static void setThumbsDownButtonIcon(JButton button, boolean voted) {
        if (voted) {
            button.setIcon(new ImageIcon(PublicationUtils.class.getResource("/platillostipicos/appdesktop/utils/imgs/hand-thumbs-down-fill.png")));
        } else {
            button.setIcon(new ImageIcon(PublicationUtils.class.getResource("/platillostipicos/appdesktop/utils/imgs/hand-thumbs-down.png")));
        }
    }

    public static JButton getThumbsUpButton(Container parent) {
        for (Component component : parent.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getIcon().toString().contains("hand-thumbs-up")) {
                    return button;
                }
            }
        }
        return null;
    }

    public static JButton getThumbsDownButton(Container parent) {
        for (Component component : parent.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (button.getIcon().toString().contains("hand-thumbs-down")) {
                    return button;
                }
            }
        }
        return null;
    }
}

