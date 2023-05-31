package platillostipicos.appdesktop.utils;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextArea;

public class FormUtils {

    public static void setPlaceholderText(JTextArea textArea, String placeholder) {
        textArea.setText(placeholder);
        textArea.setForeground(Color.GRAY);

        textArea.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                clearPlaceholderText(textArea, placeholder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                restorePlaceholderText(textArea, placeholder);
            }
        });
    }

    private static void clearPlaceholderText(JTextArea textArea, String placeholder) {
        if (textArea.getText().equals(placeholder)) {
            textArea.setText("");
            textArea.setForeground(Color.BLACK);
        }
    }

    private static void restorePlaceholderText(JTextArea textArea, String placeholder) {
        if (textArea.getText().isEmpty()) {
            setPlaceholderText(textArea, placeholder);
        }
    }
}
